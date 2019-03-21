package com.orionst.notist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.orionst.notist.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme_BaseTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(bottom_app_bar)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
    }

    override fun onBackPressed() {
        if (!navController.popBackStack())
            finish()
    }
}