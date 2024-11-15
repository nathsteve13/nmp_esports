package com.ubaya.nmp_esports

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.viewpager2.widget.ViewPager2
import com.ubaya.nmp_esports.databinding.ActivityMenuBinding
import com.google.android.material.bottomnavigation.BottomNavigationView;

class MenuActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMenuBinding
    val fragments: ArrayList<Fragment> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fragments.add(PlayFragment())
        fragments.add(WhoWeAreFragment())
        fragments.add(ScheduleFragment())
        binding.viewPager.adapter = MyAdapter(this, fragments)
        binding.viewPager.registerOnPageChangeCallback(object:
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                binding.bottomNavigationView.selectedItemId =
                    binding.bottomNavigationView.menu.getItem(position).itemId
            }
        }
        )
        binding.bottomNavigationView.setOnItemSelectedListener {
            binding.viewPager.currentItem = when(it.itemId) {
                R.id.whatweplay -> 0
                R.id.whoweare -> 1
                R.id.schedule -> 2
                else -> 0 // default to home
            }
            true
        }
    }

}