package com.orionst.notist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import com.orionst.notist.R
import com.orionst.notist.navigation.BottomNavigationFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)

        bottom_app_bar.replaceMenu(R.menu.main_bottomappbar_menu)
        bottom_app_bar.setNavigationOnClickListener {
            val bottomNavDrawerFragment = BottomNavigationFragment()
            bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
        }

        bottom_app_bar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_item_app_bar_search -> {
                    Snackbar.make(layout_root, "Text menu", Snackbar.LENGTH_SHORT)
                        .setAnchorView(fab)
                        .show()
                    true
                }
                else -> false
            }
        }
    }

    override fun onBackPressed() {
        navController.popBackStack()
    }

}
