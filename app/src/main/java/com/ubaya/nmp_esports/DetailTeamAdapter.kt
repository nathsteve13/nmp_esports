package com.ubaya.nmp_esports

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.nmp_esports.databinding.MemberCardBinding

class DetailTeamAdapter(
    private val members: List<String>,
    private val roles: List<String>
) : RecyclerView.Adapter<DetailTeamAdapter.DetailTeamViewHolder>() {

    class DetailTeamViewHolder(val binding: MemberCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DetailTeamViewHolder {
        val binding = MemberCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return DetailTeamViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return members.size
    }

    override fun onBindViewHolder(holder: DetailTeamViewHolder, position: Int) {
        holder.binding.txtName.text = members[position]
        holder.binding.txtRole.text = roles[position]
        holder.binding.imgMember.setImageResource(R.drawable.profile_1)
    }
}
