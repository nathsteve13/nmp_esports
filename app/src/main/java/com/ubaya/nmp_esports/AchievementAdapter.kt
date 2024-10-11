package com.ubaya.nmp_esports

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.nmp_esports.GameAdapter.GameViewHolder
import com.ubaya.nmp_esports.databinding.ActivityAchievementDetailBinding
import com.ubaya.nmp_esports.databinding.GameCardBinding

class AchievementAdapter() : RecyclerView.Adapter<AchievementAdapter.AchievementViewHolder>() {
    class AchievementViewHolder(val binding: ActivityAchievementDetailBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AchievementViewHolder {
        val binding = ActivityAchievementDetailBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)
        return AchievementViewHolder(binding)

    }

    override fun onBindViewHolder(holder: AchievementViewHolder, position: Int) {
        val achievement = AchievementData.achievement[position]
        val achievementText = "${achievement.achievementTitle} in ${achievement.achievementGame} (${achievement.achievementDate}), Team: ${achievement.achievementTeam}"

        holder.binding.txtAchievement.text = achievementText
    }

    override fun getItemCount(): Int {
        return AchievementData.achievement.size
    }

}