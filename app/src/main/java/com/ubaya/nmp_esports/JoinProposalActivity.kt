package com.ubaya.nmp_esports

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ubaya.nmp_esports.databinding.ActivityJoinProposalBinding
import org.json.JSONArray
import org.json.JSONObject

class JoinProposalActivity : AppCompatActivity() {

    private lateinit var binding: ActivityJoinProposalBinding
    private lateinit var joinProposalAdapter: JoinProposalAdapter
    private val proposalList = mutableListOf<proposal>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinProposalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup RecyclerView
        joinProposalAdapter = JoinProposalAdapter(proposalList)
        binding.recProposal.apply {
            layoutManager = LinearLayoutManager(this@JoinProposalActivity)
            adapter = joinProposalAdapter
        }

        // Handle FAB for adding new proposals
        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddProposalActivity::class.java)
            startActivity(intent)
        }

        // Fetch proposal data from the server
        fetchProposalData()
    }

    private fun fetchProposalData() {
        val sharedPref = getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val userId = sharedPref.getInt("userId", -1)
        Log.d("DEBUG", "userId retrieved: $userId")
        if (userId == -1) {
            Toast.makeText(this, "User ID not found in session.", Toast.LENGTH_SHORT).show()
            return
        }

        val url = "https://ubaya.xyz/native/160422124/get_proposal.php"
        val params = JSONObject().apply {
            put("idmember", userId)
        }

        Log.d("DEBUG", "Fetching proposals for userId: $userId") // Debug log

        val jsonObjectRequest = object : JsonObjectRequest(Request.Method.POST, url, params,
            Response.Listener { response ->
                try {
                    Log.d("DEBUG", "Response from server: $response") // Debug log
                    val result = response.getString("result")
                    if (result == "success") {
                        val dataArray: JSONArray = response.getJSONArray("data")
                        handleProposalData(dataArray)
                    } else {
                        val message = response.optString("message", "Gagal mendapatkan data.")
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Error parsing data: ${e.message}", Toast.LENGTH_LONG).show()
                    Log.e("API_ERROR", "Parsing error: ${e.message}")
                }
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "Error fetching data: ${error.message}", Toast.LENGTH_LONG).show()
                Log.e("API_ERROR", "Fetching error: ${error.message}")
            }) {
            override fun getHeaders(): MutableMap<String, String> {
                return mutableMapOf("Content-Type" to "application/json")
            }
        }

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(jsonObjectRequest)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun handleProposalData(dataArray: JSONArray) {
        if (dataArray.length() == 0) {
            Toast.makeText(this, "Tidak ada data ditemukan.", Toast.LENGTH_LONG).show()
            return
        }

        proposalList.clear()
        for (i in 0 until dataArray.length()) {
            try {
                val proposalObject = dataArray.getJSONObject(i)
                val idJoinProposal = proposalObject.getInt("idjoin_proposal")
                val idMember = proposalObject.getInt("idmember")
                val idTeam = proposalObject.getInt("idteam")
                val description = proposalObject.getString("description")
                val status = proposalObject.getString("status")
                val teamName = proposalObject.getString("name")

                proposalList.add(proposal(idJoinProposal, idMember, idTeam, description, status, teamName))
            } catch (e: Exception) {
                Log.e("API_ERROR", "Error parsing proposal object: ${e.message}")
            }
        }

        // Notify adapter about data change
        joinProposalAdapter.notifyDataSetChanged()
        Log.d("DEBUG", "Proposals loaded: ${proposalList.size}") // Debug log
    }
}
