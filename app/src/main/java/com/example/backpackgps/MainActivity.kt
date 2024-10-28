package com.example.backpackgps

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.backpackgps.fragments.HomeFragment
import com.example.backpackgps.fragments.MapPruebasFragment
import com.example.backpackgps.fragments.SettingsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val homeFragment = HomeFragment()
        val mapPruebasFragment = MapPruebasFragment()
        val settingsFragment = SettingsFragment()

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    setCurrentFragment(homeFragment)
                    true
                }
                R.id.navigation_map -> {
                    setCurrentFragment(mapPruebasFragment)
                    true
                }
                R.id.navigation_settings -> {
                    setCurrentFragment(settingsFragment)
                    true
                }
                else -> false
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.containerView, fragment)
            commit()
        }
    }
}
