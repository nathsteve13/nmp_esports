package com.ubaya.nmp_esports

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.nmp_esports.databinding.GameCardBinding

class GameAdapter() : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {
    class GameViewHolder(val binding:GameCardBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = GameCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)
        return GameViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return gameData.games.size
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.binding.imgGame.setImageResource(gameData.games[position].imageId)
        holder.binding.txtGameTitle.text = gameData.games[position].gameTitle

        holder.binding.btnAchievement.setOnClickListener{
            val intent = Intent(holder.itemView.context, AchievementDetailActivity::class.java)
            intent.putExtra("game_index", position)
            holder.itemView.context.startActivity(intent)
        }
        holder.binding.btnTeam.setOnClickListener{
            val intent = Intent(holder.itemView.context, TeamActivity::class.java)
            intent.putExtra("team_index", position)
            intent.putExtra("game_image", gameData.games[position].imageId)
            holder.itemView.context.startActivity(intent)
        }

    }
}
