package com.ubaya.nmp_esports

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.nmp_esports.AchievementAdapter.AchievementViewHolder
import com.ubaya.nmp_esports.databinding.ActivityAchievementDetailBinding
import com.ubaya.nmp_esports.databinding.ActivityTeamDetailBinding

class DetailTeamAdapter : RecyclerView.Adapter<DetailTeamAdapter.DetailTeamViewHolder>(){
    class DetailTeamViewHolder(val binding: ActivityTeamDetailBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DetailTeamAdapter.DetailTeamViewHolder {
        val binding = ActivityTeamDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)
        return DetailTeamViewHolder(binding)
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: DetailTeamAdapter.DetailTeamViewHolder, position: Int) {

    }
}