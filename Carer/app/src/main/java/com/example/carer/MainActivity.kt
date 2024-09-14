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
        Log.d("MainActivity", "onCreate called")

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val bottom_bar = findViewById<BottomNavigationView>(R.id.bottom_bar)

        bottom_bar.setOnItemSelectedListener {
            Log.d("MainActivity", "BottomNavigationView item selected: ${it.itemId}")

            if (it.itemId == R.id.nav_gaurd) {
                Log.d("MainActivity", "Guard fragment selected")
                inflateFragment()
            }

            true
        }

        // Debugging log to check default selected item
        Log.d("MainActivity", "Setting default selected item in BottomNavigationView")
        bottom_bar.selectedItemId = R.id.nav_home
    }

    private fun inflateFragment() {
        Log.d("MainActivity", "Inflating GuardFragment")

        // Fragment transaction debugging
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, GuardFragment.newInstance())

        // Check if fragment is actually being committed
        transaction.commit()
        Log.d("MainActivity", "Fragment transaction committed")
    }
}
