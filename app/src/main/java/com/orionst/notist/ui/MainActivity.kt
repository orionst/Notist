package com.orionst.notist.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.orionst.notist.R
import com.orionst.notist.navigation.BottomNavigationFragment
import com.orionst.notist.navigation.INavigation.INoteListNavigation

class MainActivity : AppCompatActivity(), INoteListNavigation {

    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        val layoutRoot = findViewById<CoordinatorLayout>(R.id.layout_root)

        val bottomAppBar = findViewById<BottomAppBar>(R.id.bottom_app_bar)
        bottomAppBar.replaceMenu(R.menu.main_bottomappbar_menu)
        bottomAppBar.setNavigationOnClickListener {
            val bottomNavDrawerFragment = BottomNavigationFragment()
            bottomNavDrawerFragment.show(supportFragmentManager, bottomNavDrawerFragment.tag)
        }
        val fab = findViewById<FloatingActionButton>(R.id.fab);
        bottomAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.menu_item_app_bar_search -> {
                    Snackbar.make(layoutRoot, "Text menu", Snackbar.LENGTH_SHORT)
                        .setAnchorView(fab)
                        .show()
                    true
                }
                else -> false
            }
        }
    }

    override fun onAddNewNoteClick() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
