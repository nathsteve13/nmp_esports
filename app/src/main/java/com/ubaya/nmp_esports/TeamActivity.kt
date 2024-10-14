package com.ubaya.nmp_esports

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.nmp_esports.databinding.ActivityPlayBinding
import com.ubaya.nmp_esports.databinding.ActivityTeamBinding

class TeamActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeamBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val index = intent.getIntExtra("team_index", 0)
        val selectedGame = gameData.games[index].imageId
        binding.imgViewGame.setImageResource(selectedGame)

        binding.recTeam.layoutManager = LinearLayoutManager(this)
        binding.recTeam.setHasFixedSize(true)
        binding.recTeam.adapter = TeamAdapter()
    }
}