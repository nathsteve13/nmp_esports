package com.ubaya.nmp_esports

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.nmp_esports.databinding.TeamCardBinding

class TeamAdapter(
    private val teamArray: Array<Team>,
    private val gameArray: Array<game>
) : RecyclerView.Adapter<TeamAdapter.TeamViewHolder>() {
    class TeamViewHolder(val binding: TeamCardBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamAdapter.TeamViewHolder {
        val binding = TeamCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)
        return TeamViewHolder(binding)
    }

    private fun getGameNameById(idGame: String, gameArray: Array<game>): String? {
        val game = gameArray.find { it.idGame == idGame }
        return game?.gameTitle
    }


    override fun onBindViewHolder(holder: TeamAdapter.TeamViewHolder, position: Int) {
        val team = teamArray[position]
        val gameName = getGameNameById(team.idGame, gameArray)
        holder.binding.txtTeamName.text = team.teamName
        holder.binding.txtGameName.text = gameName ?: "Unknown Game"

        holder.binding.btnStatus.setOnClickListener {
            val intent = Intent(holder.itemView.context, TeamDetail::class.java)
            intent.putExtra("team_index", team.idTeam)
            intent.putExtra("game_index", team.idGame)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return teamArray.size
    }

}