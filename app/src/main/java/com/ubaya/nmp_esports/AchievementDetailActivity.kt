package com.ubaya.nmp_esports

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.ubaya.nmp_esports.databinding.ActivityAchievementDetailBinding
import org.json.JSONArray
import org.json.JSONObject

class AchievementDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAchievementDetailBinding
    private val achievementList = mutableListOf<Achievement>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAchievementDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mendapatkan data yang dikirimkan melalui Intent
        val gameId = intent.getStringExtra("game_id") ?: "0"
        val gameName = intent.getStringExtra("game_name") ?: "Unknown Game"
        val gameImage = intent.getStringExtra("game_image") ?: ""

        // Menampilkan informasi game yang diterima
        binding.txtGameName.text = gameName  // Menampilkan nama game
        Picasso.get().load(gameImage).into(binding.imgGame)  // Menampilkan gambar game

        // Kembali ke halaman sebelumnya
        binding.btnBack.setOnClickListener {
            val intent = Intent(this, PlayActivity::class.java)
            startActivity(intent)
        }

        // Ambil data achievement dari API menggunakan Volley
        fetchAchievements(gameId)
    }

    private fun fetchAchievements(selectedGameId: String) {
        // URL API untuk mendapatkan achievements berdasarkan gameId
        val url = "https://ubaya.xyz/native/160422124/get_achievements.php?idgame=$selectedGameId"
        val queue = Volley.newRequestQueue(this)

        val request = JsonObjectRequest(Request.Method.GET, url, null,
            { response ->
                try {
                    val result = response.getString("result")
                    if (result == "success") {
                        val data: JSONArray = response.getJSONArray("data")
                        achievementList.clear()

                        // Parse data prestasi dan filter berdasarkan idgame
                        for (i in 0 until data.length()) {
                            val achievementObject: JSONObject = data.getJSONObject(i)
                            val title = achievementObject.getString("name")
                            val date = achievementObject.getString("date")
                            val team = achievementObject.getString("nama_tim")
                            val description = achievementObject.getString("description")

                            // Menambahkan achievement ke dalam list
                            val achievement = Achievement(title, selectedGameId, date, team, description)
                            achievementList.add(achievement)
                        }

                        // Menampilkan spinner dengan tahun yang berbeda
                        val years = achievementList
                            .map { it.achievementDate }
                            .distinct()
                            .toMutableList()
                        years.add(0, "All")

                        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, years)
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                        binding.spinnerDate.adapter = adapter

                        // Filter dan tampilkan achievement sesuai tahun yang dipilih
                        binding.spinnerDate.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                                val selectedYear = years[position]

                                val filteredAchievements: List<Achievement> = if (selectedYear == "All") {
                                    achievementList.filter { it.achievementGame == selectedGameId }
                                } else {
                                    achievementList.filter { it.achievementGame == selectedGameId && it.achievementDate == selectedYear }
                                }

                                val displayText = if (filteredAchievements.isEmpty()) {
                                    "No achievement in $selectedYear"
                                } else {
                                    filteredAchievements.joinToString(separator = "\n") { achievement ->
                                        "${achievement.achievementTitle} in ${achievement.achievementGame} (${achievement.achievementDate}), Team: ${achievement.achievementTeam}"
                                    }
                                }

                                binding.txtAchievement.text = displayText
                            }

                            override fun onNothingSelected(p0: AdapterView<*>?) {
                                // Implement if needed
                            }
                        }
                    } else {
                        Toast.makeText(this, "Failed to load achievements", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this, "Error parsing data: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            },
            { error ->
                Toast.makeText(this, "Failed to fetch data", Toast.LENGTH_SHORT).show()
            })

        queue.add(request)
    }
}
