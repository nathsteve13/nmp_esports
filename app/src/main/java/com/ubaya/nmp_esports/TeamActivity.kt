package com.ubaya.nmp_esports

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ubaya.nmp_esports.databinding.ActivityPlayBinding
import com.ubaya.nmp_esports.databinding.ActivityTeamBinding

class TeamActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTeamBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityTeamBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.
    }
}