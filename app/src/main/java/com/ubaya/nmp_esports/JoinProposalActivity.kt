package com.ubaya.nmp_esports

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ubaya.nmp_esports.databinding.ActivityJoinProposalBinding
import org.json.JSONArray
import org.json.JSONObject

class JoinProposalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityJoinProposalBinding
    private val proposalList = mutableListOf<proposal>()
    private lateinit var proposalAdapter: JoinProposalAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityJoinProposalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPref = getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val userId = sharedPref.getInt("userId", -1)

        Log.d("DEBUG_SHARED_PREF", "User ID: $userId")

        if (userId == -1) {
            Toast.makeText(this, "Session expired, please login again.", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            proposalAdapter = JoinProposalAdapter(proposalList)
            binding.recProposal.layoutManager = LinearLayoutManager(this)
            binding.recProposal.setHasFixedSize(true)
            binding.recProposal.adapter = proposalAdapter

            fetchJoinProposals(userId)
        }

        binding.fabAdd.setOnClickListener {
            val intent = Intent(this, AddProposalActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchJoinProposals(userId: Int) {
        val url = "https://ubaya.xyz/native/160422124/get_proposal.php?idmember=$userId"
        val queue = Volley.newRequestQueue(this)

        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    Log.d("DEBUG_API", "Response: $response")
                    val result = response.getString("result")
                    if (result == "success") {
                        val data: JSONArray = response.getJSONArray("data")
                        for (i in 0 until data.length()) {
                            val proposalObject: JSONObject = data.getJSONObject(i)
                            val idJoinProposal = proposalObject.getInt("idjoin_proposal")
                            val idMember = proposalObject.getInt("idmember")
                            val idTeam = proposalObject.getInt("idteam")
                            val description = proposalObject.getString("description")
                            val status = proposalObject.getString("status")
                            val teamName = proposalObject.getString("name")

                            val joinProposal = proposal(
                                idJoinProposal,
                                idMember,
                                idTeam,
                                description,
                                status,
                                teamName
                            )
                            proposalList.add(joinProposal)
                        }

                        Log.d("DEBUG_LIST_SIZE", "Proposal Count: ${proposalList.size}")
                        proposalAdapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(this, "No proposals found.", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Error parsing data: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Log.e("ERROR", "Error fetching data: ${error.message}")
                Toast.makeText(this, "Failed to fetch data.", Toast.LENGTH_SHORT).show()
            })

        queue.add(request)
    }
}
