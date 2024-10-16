package com.ubaya.nmp_esports

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.nmp_esports.databinding.ActivityTeamBinding

class TeamActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeamBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val index = intent.getIntExtra("team_index", 0)
        val selectedGame = gameData.games[index]
        val selectedGameId = selectedGame.idGame
        val selectedGameImage = selectedGame.imageId

        val filteredTeams = TeamData.team.filter { it.idGame == selectedGameId }

        binding.imgViewGame.setImageResource(selectedGameImage)

        binding.recTeam.layoutManager = LinearLayoutManager(this)
        binding.recTeam.setHasFixedSize(true)
        binding.recTeam.adapter = TeamAdapter(filteredTeams.toTypedArray(), gameData.games)

        binding.btnBack.setOnClickListener{
            val intent = Intent(this, PlayActivity::class.java)
            startActivity(intent)
        }

    }
}