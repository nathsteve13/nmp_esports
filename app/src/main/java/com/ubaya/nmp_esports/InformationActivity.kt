package com.ubaya.nmp_esports

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ubaya.nmp_esports.databinding.ActivityInformationBinding
import com.ubaya.nmp_esports.databinding.FragmentHomeBinding

class InformationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInformationBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var numLike = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInformationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences("game_prefs", MODE_PRIVATE)

        numLike = sharedPreferences.getInt("num_like", 0)
        binding.txtLikeNum.text = numLike.toString()

        binding.backHomeBtn.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        binding.btnLike.setOnClickListener {
            numLike += 1
            binding.txtLikeNum.text = numLike.toString()

            sharedPreferences.edit().putInt("num_like", numLike).apply()
        }
    }
}