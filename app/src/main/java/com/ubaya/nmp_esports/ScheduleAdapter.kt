package com.ubaya.nmp_esports

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.ubaya.nmp_esports.databinding.ScheduleCardBinding
import org.json.JSONArray
import org.json.JSONObject

class ScheduleAdapter(private val scheduleList: List<Schedule>) : RecyclerView.Adapter<ScheduleAdapter.ScheduleViewHolder>() {

    class ScheduleViewHolder(val binding: ScheduleCardBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScheduleViewHolder {
        val binding = ScheduleCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return ScheduleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return scheduleList.size
    }

    override fun onBindViewHolder(holder: ScheduleViewHolder, position: Int) {
        val schedule = scheduleList[position]

        holder.binding.txtScheduleTitle.text = schedule.title
        holder.binding.txtScheduleTime.text = schedule.date

        Picasso.get()
            .load(schedule.imageUrl)
            .into(holder.binding.imgSchedule)

        holder.binding.btnDetail.setOnClickListener {
            val context = holder.itemView.context
            val intent = Intent(context, ScheduleDetailActivity::class.java)

            intent.putExtra("schedule_id", schedule.idschedule)
            intent.putExtra("SCHEDULE_TITLE", schedule.title)
            intent.putExtra("SCHEDULE_TIME", schedule.date)
            intent.putExtra("DESCRIPTION", schedule.description)
            intent.putExtra("IMAGE_URL", schedule.imageUrl)

            context.startActivity(intent)
        }
    }
}
