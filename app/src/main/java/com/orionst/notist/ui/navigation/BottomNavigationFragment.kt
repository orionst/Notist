package com.orionst.notist.ui.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.navigation.NavigationView
import com.orionst.notist.R

class BottomNavigationFragment : BottomSheetDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val layout = inflater.inflate(R.layout.fragment_bottomsheet_navigation_drawer, container)
        val navigationView = layout.findViewById<NavigationView>(R.id.navigation_view)
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav1 -> {
                    Toast.makeText(getContext(), "Nav 1", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav2 -> {
                    Toast.makeText(getContext(), "Nav 2", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav3 -> {
                    Toast.makeText(getContext(), "Nav 3", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        return layout
    }


}