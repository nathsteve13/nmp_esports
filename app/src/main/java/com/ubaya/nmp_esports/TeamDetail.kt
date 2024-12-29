package com.ubaya.nmp_esports

import android.annotation.SuppressLint
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
import com.ubaya.nmp_esports.databinding.ActivityTeamDetailBinding
import org.json.JSONArray
import org.json.JSONObject

class TeamDetail : AppCompatActivity() {
    private lateinit var binding: ActivityTeamDetailBinding

    private val memberList = mutableListOf<String>()
    private val roleList = mutableListOf<String>()
    private lateinit var adapter: DetailTeamAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val teamId = intent.getIntExtra("team_id", 0)
        val gameId = intent.getIntExtra("game_id", 0)
        val teamName = intent.getStringExtra("team_name") ?: "Unknown Team"

        binding.txtNamaTeam.text = teamName

        adapter = DetailTeamAdapter(memberList, roleList)
        binding.recTeamDetails.layoutManager = LinearLayoutManager(this)
        binding.recTeamDetails.adapter = adapter

        fetchTeamMembers(teamId)

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, TeamActivity::class.java)
            intent.putExtra("game_id", gameId)
            startActivity(intent)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun fetchTeamMembers(teamId: Int) {
        val url = "https://ubaya.xyz/native/160422124/get_team_members.php?idteam=$teamId"
        val queue = Volley.newRequestQueue(this)

        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    val result = response.getString("result")
                    if (result == "success") {
                        val data: JSONArray = response.getJSONArray("data")

                        memberList.clear()
                        roleList.clear()

                        for (i in 0 until data.length()) {
                            val memberObject: JSONObject = data.getJSONObject(i)
                            val name = memberObject.getString("name")
                            val role = memberObject.getString("role")

                            memberList.add(name)
                            roleList.add(role)
                        }

                        adapter.notifyDataSetChanged()
                    } else {
                        Toast.makeText(this, "Tidak ada anggota tim ditemukan", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Error parsing data: ${e.message}", Toast.LENGTH_SHORT).show()
                    Log.e("API_ERROR", "Parsing error: ${e.message}")
                }
            },
            { error ->
                Toast.makeText(this, "Gagal mengambil data anggota tim", Toast.LENGTH_SHORT).show()
                Log.e("API_ERROR", "Fetching error: ${error.message}")
            })

        queue.add(request)
    }
}
