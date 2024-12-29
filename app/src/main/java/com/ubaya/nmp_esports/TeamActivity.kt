package com.ubaya.nmp_esports

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ubaya.nmp_esports.databinding.ActivityTeamBinding
import org.json.JSONArray
import org.json.JSONObject

class TeamActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeamBinding
    private val teamList = mutableListOf<Team>()
    private lateinit var teamAdapter: TeamAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gameId = intent.getIntExtra("game_id", 0)
        val gameName = intent.getStringExtra("game_name")
        val gameImage = intent.getStringExtra("game_image")

        Log.d("DEBUG_INTENT", "Game ID: $gameId, Game Name: $gameName")

        teamAdapter = TeamAdapter(teamList)
        binding.recTeam.layoutManager = LinearLayoutManager(this)
        binding.recTeam.setHasFixedSize(true)
        binding.recTeam.adapter = teamAdapter

        fetchTeams(gameId)

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, PlayActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchTeams(gameId: Int) {
        val url = "https://ubaya.xyz/native/160422124/get_teams.php?idgame=$gameId"
        val queue = Volley.newRequestQueue(this)

        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    Log.d("DEBUG_API", "Response: $response")
                    val result = response.getString("result")
                    if (result == "success") {
                        val data: JSONArray = response.getJSONArray("data")
                        teamList.clear()

                        for (i in 0 until data.length()) {
                            val teamObject: JSONObject = data.getJSONObject(i)
                            val idteam = teamObject.getInt("idteam")
                            val name = teamObject.getString("name")

                            val team = Team(idteam, gameId, name)
                            teamList.add(team)
                        }

                        Log.d("DEBUG_LIST_SIZE", "Team Count: ${teamList.size}")
                        teamAdapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(this, "No teams found.", Toast.LENGTH_SHORT).show()
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
