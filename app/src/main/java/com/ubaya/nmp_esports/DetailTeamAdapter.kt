package com.ubaya.nmp_esports

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.nmp_esports.AchievementAdapter.AchievementViewHolder
import com.ubaya.nmp_esports.DetailTeamData.detailTeam
import com.ubaya.nmp_esports.databinding.ActivityAchievementDetailBinding
import com.ubaya.nmp_esports.databinding.ActivityTeamDetailBinding
import com.ubaya.nmp_esports.databinding.MemberCardBinding

class DetailTeamAdapter : RecyclerView.Adapter<DetailTeamAdapter.DetailTeamViewHolder>(){
    class DetailTeamViewHolder(val binding: MemberCardBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailTeamViewHolder {
        val binding = MemberCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailTeamViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return DetailTeamData.detailTeam.size
    }

    override fun onBindViewHolder(holder: DetailTeamAdapter.DetailTeamViewHolder, position: Int) {
        holder.binding.txtName.text = detailTeam[position].members[position]
        holder.binding.txtRole.text = detailTeam[position].roles[position]
    }
}