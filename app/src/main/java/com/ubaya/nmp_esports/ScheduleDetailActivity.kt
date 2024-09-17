package com.ubaya.nmp_esports

import android.app.AlertDialog
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ubaya.nmp_esports.databinding.ActivityInformationBinding
import com.ubaya.nmp_esports.databinding.ActivityScheduleDetailBinding

class ScheduleDetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScheduleDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val scheduleTitle:String = intent.getStringExtra(ScheduleFragment.SCHEDULE_TITLE).toString()
        val scheduleTime:String = intent.getStringExtra(ScheduleFragment.SCHEDULE_TIME).toString()
        val description:String = intent.getStringExtra(ScheduleFragment.DESCRIPTION).toString()

        binding.txtTitle.text = scheduleTitle
        binding.txtTime.text = scheduleTime
        binding.txtDescription.text = description

        binding.btnNotify.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Notification")
                .setMessage("Notification created")
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }
    }
}