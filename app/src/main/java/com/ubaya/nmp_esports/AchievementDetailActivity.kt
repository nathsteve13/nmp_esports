package com.ubaya.nmp_esports

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ubaya.nmp_esports.databinding.ActivityAchievementDetailBinding

class AchievementDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAchievementDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAchievementDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val index = intent.getIntExtra("game_index", 0)
        val selectedGame = gameData.games[index].gameTitle
        binding.txtGameName.setText(selectedGame)

        val imgId = gameData.games[index].imageId
        binding.imgGame.setImageResource(imgId)

        val years = AchievementData.achievement
            .filter { it.achievementGame == selectedGame }
            .map { it.achievementDate }
            .distinct()
            .toMutableList()
        years.add(0, "All")

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, years)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerDate.adapter = adapter

        binding.spinnerDate.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedYear = years[position]

                val filteredAchievements: List<Achievement> = if (selectedYear == "All") {
                    AchievementData.achievement.filter { it.achievementGame == selectedGame }
                } else {
                    AchievementData.achievement.filter {
                        it.achievementGame == selectedGame && it.achievementDate == selectedYear
                    }
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
                TODO("Not yet implemented")
            }

        }
    }
}