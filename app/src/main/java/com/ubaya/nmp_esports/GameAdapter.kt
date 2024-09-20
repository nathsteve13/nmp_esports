package com.ubaya.nmp_esports

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

    }
}
