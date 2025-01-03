package com.ubaya.nmp_esports

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ubaya.nmp_esports.databinding.ActivityAddProposalBinding
import org.json.JSONArray
import org.json.JSONObject

class AddProposalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAddProposalBinding
    private val gameList = mutableListOf<String>()
    private val gameIdList = mutableListOf<Int>()
    private val teamList = mutableListOf<String>()
    private val teamIdList = mutableListOf<Int>()
    private var selectedGameId: Int? = null
    private var selectedTeamId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddProposalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadGames()

        binding.spinnerGame.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedGameId = gameIdList[position]
                loadTeams(selectedGameId!!)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.spinnerTeam.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectedTeamId = teamIdList[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        binding.btnApply.setOnClickListener {
            val description = binding.editDescription.text.toString().trim()
            if (selectedGameId != null && selectedTeamId != null && description.isNotBlank()) {
                submitProposal(selectedGameId!!, selectedTeamId!!, description)
            } else {
                Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loadGames() {
        val url = "https://ubaya.xyz/native/160422124/get_games.php"
        val requestQueue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    val dataArray: JSONArray = response.getJSONArray("data")
                    gameList.clear()
                    gameIdList.clear()
                    for (i in 0 until dataArray.length()) {
                        val gameObject = dataArray.getJSONObject(i)
                        gameList.add(gameObject.getString("name"))
                        gameIdList.add(gameObject.getInt("idgame"))
                    }
                    val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, gameList)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.spinnerGame.adapter = adapter
                } catch (e: Exception) {
                    Toast.makeText(this, "Error loading games: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Toast.makeText(this, "Error fetching games: ${error.message}", Toast.LENGTH_SHORT).show()
            })

        requestQueue.add(jsonObjectRequest)
    }

    private fun loadTeams(gameId: Int) {
        val url = "https://ubaya.xyz/native/160422124/get_teams.php?idgame=$gameId"
        val requestQueue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    val dataArray: JSONArray = response.getJSONArray("data")
                    teamList.clear()
                    teamIdList.clear()
                    for (i in 0 until dataArray.length()) {
                        val teamObject = dataArray.getJSONObject(i)
                        teamList.add(teamObject.getString("name"))
                        teamIdList.add(teamObject.getInt("idteam"))
                    }
                    val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, teamList)
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    binding.spinnerTeam.adapter = adapter
                } catch (e: Exception) {
                    Toast.makeText(this, "Error loading teams: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Toast.makeText(this, "Error fetching teams: ${error.message}", Toast.LENGTH_SHORT).show()
            })

        requestQueue.add(jsonObjectRequest)
    }

    private fun submitProposal(gameId: Int, teamId: Int, description: String) {
        val sharedPref = getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val userId = sharedPref.getInt("userId", -1)
        if (userId == -1) {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            return
        }

        val url = "https://ubaya.xyz/native/160422124/submit_proposal.php"
        val params = JSONObject()
        params.put("idmember", userId)
        params.put("idgame", gameId)
        params.put("idteam", teamId)
        params.put("description", description)

        Log.d("DEBUG", "Parameters sent: $params")

        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, url, params,
            { response ->
                try {
                    Log.d("DEBUG", "Response: $response")
                    val result = response.getString("result")
                    if (result == "success") {
                        Toast.makeText(this, "Proposal submitted successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        val message = response.optString("message", "Failed to submit proposal.")
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Error submitting proposal: ${e.message}", Toast.LENGTH_SHORT).show()
                    Log.e("ERROR", "Parsing error: ${e.message}")
                }
            },
            { error ->
                Toast.makeText(this, "Error submitting proposal: ${error.message}", Toast.LENGTH_SHORT).show()
                Log.e("ERROR", "Request error: ${error.message}")
            })

        val requestQueue = Volley.newRequestQueue(this)
        requestQueue.add(jsonObjectRequest)
    }
}
