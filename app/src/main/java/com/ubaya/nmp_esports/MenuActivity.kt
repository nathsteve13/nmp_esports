package com.ubaya.nmp_esports

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
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
        setSupportActionBar(binding.toolbar)

        val toggle = ActionBarDrawerToggle(this, binding.drawerLayout, binding.toolbar, R.string.navigation_drawer_open,
            R.string.navigation_drawer_close)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

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

    override fun onBackPressed() {
        if (binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }else {
            super.onBackPressed()
        }

    }

}