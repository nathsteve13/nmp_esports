package com.ubaya.nmp_esports

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.ubaya.nmp_esports.databinding.ActivityMenuBinding
import com.google.android.material.bottomnavigation.BottomNavigationView;

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(PlayFragment())

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.whatweplay -> {
                    replaceFragment(PlayFragment())
                }
                R.id.whoweare -> {
                    replaceFragment(WhoWeAreFragment())
                }
                R.id.schedule -> {
                    replaceFragment(ScheduleFragment())
                }
            }
            true
        }
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, fragment)
        fragmentTransaction.commit()
    }
}