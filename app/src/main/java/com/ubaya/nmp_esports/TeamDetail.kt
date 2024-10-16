package com.ubaya.nmp_esports

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.ubaya.nmp_esports.databinding.ActivityTeamDetailBinding

class TeamDetail : AppCompatActivity() {
    private lateinit var binding: ActivityTeamDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val indexTeam = intent.getIntExtra("team_index", 0)
        val selectedTeam = DetailTeamData.detailTeam[indexTeam]

        val indexGame = intent.getStringExtra("game_index")?: ""
        binding.txtNamaTeam.text = selectedTeam.teamName

        binding.recTeamDetails.layoutManager = LinearLayoutManager(this)
        binding.recTeamDetails.setHasFixedSize(true)
        binding.recTeamDetails.adapter = DetailTeamAdapter(selectedTeam)

        binding.btnBack.setOnClickListener{
            val intent = Intent(this, TeamActivity::class.java)
            intent.putExtra("game_index", indexGame.toInt())
            startActivity(intent)
        }
    }
}