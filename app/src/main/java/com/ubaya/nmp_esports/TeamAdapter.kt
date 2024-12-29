package com.ubaya.nmp_esports

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.nmp_esports.databinding.TeamCardBinding

class TeamAdapter(private val teams: List<Team>) : RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {

    class TeamViewHolder(val binding: TeamCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val binding = TeamCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return TeamViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return teams.size
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        val team = teams[position]
        Log.d("DEBUG_ADAPTER", "Team Name: ${team.name}")

        holder.binding.txtTeamName.text = team.idgame.toString()
        holder.binding.txtTeamName.text = team.name

        holder.binding.btnStatus.setOnClickListener {
            val intent = Intent(holder.itemView.context, TeamDetail::class.java)
            intent.putExtra("team_id", team.idteam)
            holder.itemView.context.startActivity(intent)
        }
    }
}
