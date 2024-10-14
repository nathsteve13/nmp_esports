package com.ubaya.nmp_esports

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.nmp_esports.AchievementAdapter.AchievementViewHolder
import com.ubaya.nmp_esports.TeamData.team
import com.ubaya.nmp_esports.databinding.ActivityAchievementDetailBinding
import com.ubaya.nmp_esports.databinding.ActivityTeamBinding
import com.ubaya.nmp_esports.databinding.GameCardBinding
import com.ubaya.nmp_esports.databinding.TeamCardBinding

class TeamAdapter() : RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {
    class TeamViewHolder(val binding: TeamCardBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamAdapter.TeamViewHolder {
        val binding = TeamCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)
        return TeamViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TeamAdapter.TeamViewHolder, position: Int) {
        holder.binding.txtTeamName.text = TeamData.team[position].teamName

        val game = gameData.games.find { it.idGame == team[position].idGame }
        holder.binding.txtGameName.text = game?.gameTitle ?: "Unknown Game"

        holder.binding.btnView.setOnClickListener {
            val intent = Intent(holder.itemView.context, TeamActivity::class.java)
            intent.putExtra("team_index", position)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return TeamData.team.size
    }

}