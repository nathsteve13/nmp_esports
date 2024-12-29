package com.ubaya.nmp_esports

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.ubaya.nmp_esports.databinding.GameCardBinding

class GameAdapter(private val games: List<Game>) : RecyclerView.Adapter<GameAdapter.GameViewHolder>() {

    class GameViewHolder(val binding: GameCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val binding = GameCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return GameViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return games.size
    }

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        val game = games[position]

        holder.binding.txtGameTitle.text = game.name

        Picasso.get()
            .load(game.imageUrl)
            .placeholder(R.drawable.esportimage)
            .into(holder.binding.imgGame)

        holder.binding.btnAchievement.setOnClickListener {
            val intent = Intent(holder.itemView.context, AchievementDetailActivity::class.java)
            intent.putExtra("game_id", game.idgame)
            intent.putExtra("game_name", game.name)
            intent.putExtra("game_image", game.imageUrl)
            holder.itemView.context.startActivity(intent)
        }

        holder.binding.btnTeam.setOnClickListener {
            val intent = Intent(holder.itemView.context, TeamActivity::class.java)
            intent.putExtra("game_id", game.idgame)
            intent.putExtra("game_name", game.name)
            intent.putExtra("game_image", game.imageUrl)
            holder.itemView.context.startActivity(intent)
        }
    }
}
