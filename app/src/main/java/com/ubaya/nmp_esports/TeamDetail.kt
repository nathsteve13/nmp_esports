package com.ubaya.nmp_esports

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

        val index = intent.getIntExtra("team_index", 0)
        val selectedTeam = DetailTeamData.detailTeam[index]

        binding.txtNamaTeam.text = selectedTeam.teamName

        binding.recTeamDetails.layoutManager = LinearLayoutManager(this)
        binding.recTeamDetails.setHasFixedSize(true)
        binding.recTeamDetails.adapter = DetailTeamAdapter(selectedTeam)
    }
}