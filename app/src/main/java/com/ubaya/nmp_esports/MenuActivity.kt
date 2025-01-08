package com.ubaya.nmp_esports

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
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
//        setSupportActionBar(binding.toolbar)

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

        val sharedPref = getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val username = sharedPref.getString("username", "Guest")

        val headerView = binding.navView.getHeaderView(0)
        val txtName = headerView.findViewById<TextView>(R.id.txtName)
        txtName.text = username

        binding.navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_logout -> {
                    val sharedPref = getSharedPreferences("user_session", Context.MODE_PRIVATE)
                    with(sharedPref.edit()) {
                        clear()
                        apply()
                    }

                    val intent = Intent(this, LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                    true
                }

                R.id.nav_join_proposal -> {
                    val intent = Intent(this, JoinProposalActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
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