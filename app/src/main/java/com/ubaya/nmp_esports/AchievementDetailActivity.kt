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
        binding.txtGameName.setText(gameData.games[index].gameTitle)

        val imgId = gameData.games[index].imageId
        binding.imgGame.setImageResource(imgId)

        val items = arrayOf("All", "2024", "2023", "2023", "2022", "2021", "2020")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, items)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerDate.adapter = adapter

        binding.spinnerDate.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?,
                                        position: Int, id: Long) {
                val selectedItem = items[position]

                val displayText = when (selectedItem) {
                    "All" -> "Menampilkan semua data"
                    "2024" -> "Menampilkan data untuk tahun 2024"
                    "2023" -> "Menampilkan data untuk tahun 2023"
                    "2022" -> "Menampilkan data untuk tahun 2022"
                    "2021" -> "Menampilkan data untuk tahun 2021"
                    "2020" -> "Menampilkan data untuk tahun 2020"
                    else -> "Tidak ada data"
                }

                binding.txtAchievement.text = displayText

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }

        }

    }
}