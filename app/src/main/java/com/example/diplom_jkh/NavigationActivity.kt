package com.example.diplom_jkh

import android.os.Bundle
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageButton
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColor
import androidx.fragment.app.Fragment
import com.example.diplom_jkh.data.NewRequestClickListener
import com.example.diplom_jkh.ui.home.HomeFragment
import com.example.diplom_jkh.ui.notifications.NotificationsFragment
import com.example.diplom_jkh.ui.profile.ProfileFragment
import com.example.diplom_jkh.ui.requests.NewRequestFragment
import com.example.diplom_jkh.ui.requests.RequestsFragment
import com.example.diplom_jkh.ui.settings.SettingsFragment

class NavigationActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_nav_menu)
        bottomNavigation?.selectedItemId = R.id.navigation_home
        val frameLayout = findViewById<FrameLayout>(R.id.frameLayout)
        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, HomeFragment()).commit()
        bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {

                R.id.navigation_profile -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, ProfileFragment()).commit()
                    R.id.navigation_profile.toColor()
                    // Обработка нажатия на кнопку "Profile"
                    true
                }

                R.id.navigation_settings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, SettingsFragment()).commit()
                    // Обработка нажатия на кнопку "Settings"
                    true
                }

                R.id.navigation_requests -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, RequestsFragment()).commit()
                    // Обработка нажатия на кнопку "Requests"
                    true
                }

                R.id.navigation_home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, HomeFragment()).commit()
                    // Обработка нажатия на кнопку "Home"
                    true
                }

                R.id.navigation_notifications -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.frameLayout, NotificationsFragment()).commit()
                    // Обработка нажатия на кнопку "Notifications"
                    true
                }

                else -> false
            }
        }

    }
    fun openNewRequestsFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.frameLayout, NewRequestFragment())
            .addToBackStack(null)
            .commit()
    }
}

