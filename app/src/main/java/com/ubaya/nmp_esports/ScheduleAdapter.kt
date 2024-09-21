package com.ubaya.nmp_esports

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ubaya.nmp_esports.ScheduleData.schedule
import com.ubaya.nmp_esports.databinding.ScheduleCardBinding

class ScheduleAdapter: RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {
    class ScheduleViewHolder(val binding: ScheduleCardBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val binding = ScheduleCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,false)
        return ScheduleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return ScheduleData.schedule.size
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        holder.binding.imgSchedule.setImageResource(schedule[position].imageId)
        holder.binding.txtScheduleTitle.text = schedule[position].scheduleTitle
        holder.binding.txtScheduleTime.text = schedule[position].scheduleTime

        holder.binding.btnDetail.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ScheduleDetailActivity::class.java)

            intent.putExtra("SCHEDULE_TITLE", schedule[position].scheduleTitle)
            intent.putExtra("SCHEDULE_TIME", schedule[position].scheduleTime)
            intent.putExtra("DESCRIPTION", schedule[position].description)
            intent.putExtra("IMAGE_ID", schedule[position].imageId)

            context.startActivity(intent)
        }
    }


}