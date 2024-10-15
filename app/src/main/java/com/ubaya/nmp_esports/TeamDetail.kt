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

        val teamId = intent.getIntExtra("team_index", 0)

        val selectedTeam = DetailTeamData.detailTeam.find { it.idTeam == teamId }

        if (selectedTeam != null) {
            val adapter = DetailTeamAdapter(selectedTeam)
            binding.recyclerViewMembers.layoutManager = LinearLayoutManager(this)
            binding.recyclerViewMembers.adapter = adapter
        }
    }
}