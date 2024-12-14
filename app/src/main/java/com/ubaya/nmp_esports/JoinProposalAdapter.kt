package com.ubaya.nmp_esports

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.nmp_esports.GameAdapter.GameViewHolder
import com.ubaya.nmp_esports.databinding.GameCardBinding
import com.ubaya.nmp_esports.databinding.ProposalCardBinding

class JoinProposalAdapter(private val proposalList: List<proposal>) : RecyclerView.Adapter<JoinProposalAdapter.JoinProposalViewHolder>() {

    class JoinProposalViewHolder(val binding: ProposalCardBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): JoinProposalViewHolder {
        val binding = ProposalCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)
        return JoinProposalViewHolder(binding)

    }

    override fun onBindViewHolder(holder: JoinProposalViewHolder, position: Int) {
        val proposal = proposalList[position]

        holder.binding.txtId.text = proposal.idJoinProposal.toString()
        holder.binding.txtTeamName.text = proposal.teamName
        holder.binding.btnStatus.text = proposal.status

    }

    override fun getItemCount(): Int {
        return proposalList.size
    }
}
