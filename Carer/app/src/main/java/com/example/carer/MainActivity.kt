package com.example.carer

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.time.Instant

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")

    val permission = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.READ_CONTACTS
    )
    val permissionCode = 45
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)




        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        askForPermission()

        val bottom_bar = findViewById<BottomNavigationView>(R.id.bottom_bar)

        bottom_bar.setOnItemSelectedListener {


            when (it.itemId) {
                R.id.nav_gaurd -> {
                    inflateFragment(GuardFragment.newInstance())
                }

                R.id.nav_dashboard -> {
                    inflateFragment(MapsFragment())
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

    private fun askForPermission() {
     ActivityCompat.requestPermissions(this,permission,permissionCode)
    }

    private fun inflateFragment(newinstance: Fragment) {


        // Fragment transaction debugging
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, newinstance)

        // Check if fragment is actually being committed
        transaction.commit()

    }
}
