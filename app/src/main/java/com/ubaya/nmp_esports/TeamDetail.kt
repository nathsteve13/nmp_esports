package com.ubaya.nmp_esports

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ubaya.nmp_esports.databinding.ActivityPlayBinding
import com.ubaya.nmp_esports.databinding.ActivityScheduleDetailBinding
import com.ubaya.nmp_esports.databinding.ActivityTeamDetailBinding

class TeamDetail : AppCompatActivity() {
    private lateinit var binding: ActivityTeamDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTeamDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}