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
import com.squareup.picasso.Picasso
import com.ubaya.nmp_esports.databinding.ActivityTeamBinding
import org.json.JSONArray
import org.json.JSONObject

class TeamActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeamBinding

    private val teamList = mutableListOf<Team>()
    private val gameList = mutableListOf<Game>()
    private lateinit var teamAdapter: TeamAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val gameId = intent.getIntExtra("game_id", 0)
        val gameName = intent.getStringExtra("game_name") ?: "Unknown Game"
        val gameImage = intent.getStringExtra("game_image") ?: ""

        Log.d("DEBUG_INTENT", "Game ID: $gameId, Name: $gameName, Image: $gameImage")

        binding.txtNamaTeam.text = gameName

        Picasso.get()
            .load(gameImage)
            .placeholder(R.drawable.esportimage)
            .into(binding.imgViewGame)

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
                    val result = response.getString("result")
                    if (result == "success") {
                        val data: JSONArray = response.getJSONArray("data")
                        teamList.clear()

                        for (i in 0 until data.length()) {
                            val teamObject = data.getJSONObject(i)
                            val idteam = teamObject.getInt("idteam")
                            val name = teamObject.getString("name")

                            teamList.add(Team(idteam, gameId, name))
                        }

                        Log.d("DEBUG_TEAMS", "Team List: $teamList")
                        teamAdapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(this, "No teams found.", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Error parsing teams: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Log.e("ERROR_TEAMS", "Error fetching teams: ${error.message}")
                Toast.makeText(this, "Failed to fetch teams.", Toast.LENGTH_SHORT).show()
            })

        queue.add(request)
    }
}
