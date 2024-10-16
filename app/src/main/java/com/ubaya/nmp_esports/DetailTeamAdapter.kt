package com.ubaya.nmp_esports

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.nmp_esports.AchievementAdapter.AchievementViewHolder
import com.ubaya.nmp_esports.DetailTeamData.detailTeam
import com.ubaya.nmp_esports.databinding.ActivityAchievementDetailBinding
import com.ubaya.nmp_esports.databinding.ActivityTeamDetailBinding
import com.ubaya.nmp_esports.databinding.MemberCardBinding

class DetailTeamAdapter(private val detailTeam: DetailTeam) : RecyclerView.Adapter<DetailTeamAdapter.DetailTeamViewHolder>(){
    class DetailTeamViewHolder(val binding: MemberCardBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailTeamViewHolder {
        val binding = MemberCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DetailTeamViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return detailTeam.members.size
    }

    override fun onBindViewHolder(holder: DetailTeamAdapter.DetailTeamViewHolder, position: Int) {
        holder.binding.txtName.text = detailTeam.members[position]
        holder.binding.txtRole.text = detailTeam.roles[position]
        holder.binding.imgMember.setImageResource(detailTeam.idImg[position])
    }
}