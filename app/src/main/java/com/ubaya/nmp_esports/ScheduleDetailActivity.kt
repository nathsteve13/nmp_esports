package com.ubaya.nmp_esports

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso

import com.ubaya.nmp_esports.databinding.ActivityInformationBinding
import com.ubaya.nmp_esports.databinding.ActivityScheduleDetailBinding

class ScheduleDetailActivity : AppCompatActivity() {
    companion object {
        const val SCHEDULE_TITLE = "SCHEDULE_TITLE"
        const val SCHEDULE_TIME = "SCHEDULE_TIME"
        const val DESCRIPTION = "DESCRIPTION"
        const val IMAGE_URL = "IMAGE_URL"
    }

    private lateinit var binding: ActivityScheduleDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScheduleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val scheduleTitle: String = intent.getStringExtra(SCHEDULE_TITLE).orEmpty()
        val scheduleTime: String = intent.getStringExtra(SCHEDULE_TIME).orEmpty()
        val description: String = intent.getStringExtra(DESCRIPTION).orEmpty()
        val imageUrl: String = intent.getStringExtra(IMAGE_URL).orEmpty()

        binding.txtTitle.text = scheduleTitle
        binding.txtTime.text = scheduleTime
        binding.txtDescription.text = description
        Picasso.get()
            .load(imageUrl)
            .placeholder(R.drawable.esportimage)
            .into(binding.imgScheduleDetail)

        binding.btnNotify.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Notification")
                .setMessage("Notification created")
                .setPositiveButton("OK") { dialog, _ ->
                    dialog.dismiss()
                }
                .show()
        }

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }
    }
}