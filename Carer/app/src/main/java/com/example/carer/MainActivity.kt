package com.example.carer

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.time.Instant

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Debugging log to check if onCreate is called


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bottom_bar = findViewById<BottomNavigationView>(R.id.bottom_bar)

        bottom_bar.setOnItemSelectedListener {


            when(it.itemId){
                R.id.nav_gaurd -> {
                    inflateFragment(GuardFragment.newInstance())
                }
                R.id.nav_dashboard -> {
                    inflateFragment(DashboardFragment.newInstance())
                }
                R.id.nav_home -> {
                    inflateFragment(HomeFragment.newInstance())
                }
                R.id.nav_profile -> {
                    inflateFragment(ProfileFragment.newInstance())
                }
            }

            true
        }

        // Debugging log to check default selected item

        bottom_bar.selectedItemId = R.id.nav_home
    }

    private fun inflateFragment(newinstance : Fragment) {


        // Fragment transaction debugging
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, newinstance)

        // Check if fragment is actually being committed
        transaction.commit()

    }
}
