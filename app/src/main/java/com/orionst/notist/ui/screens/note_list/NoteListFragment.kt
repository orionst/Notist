package com.orionst.notist.ui.screens.note_list

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.snackbar.Snackbar
import com.orionst.notist.R
import com.orionst.notist.data.entity.Note
import com.orionst.notist.ui.IWidgetTuning
import com.orionst.notist.ui.base.BaseFragment
import com.orionst.notist.ui.navigation.BottomNavigationFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_note_list.*


class NoteListFragment : BaseFragment<List<Note>?, NotesViewState>(), IWidgetTuning {

    private lateinit var adapter: NotesRecyclerViewAdapter
    private val bottomNavDrawerFragment: BottomNavigationFragment = BottomNavigationFragment()

    override val viewModel: NoteListViewModel by lazy {
        ViewModelProviders.of(this).get(NoteListViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.let {
            it.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        }?.let {
            it.hideSoftInputFromWindow(view!!.windowToken, 0)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.fragment_note_list, container, false)

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        rv_notes.layoutManager = GridLayoutManager(context, 2)
        adapter = NotesRecyclerViewAdapter()
        rv_notes.adapter = adapter

        bottomAppBarTune()
    }

    override fun renderData(data: List<Note>?) {
        data?.let {
            adapter.notes = it
        }
    }

    override fun bottomAppBarTune() {
        activity?.let {
            it.fab.show()
            it.bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            it.bottom_app_bar.setNavigationIcon(R.drawable.ic_vector_menu_white)

            it.fab.setImageDrawable(context?.getDrawable(R.drawable.ic_vector_add))
            it.fab.setOnClickListener {
                val action = NoteListFragmentDirections.actionOpenNote()
                findNavController().navigate(action)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_bottomappbar_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                bottomNavDrawerFragment.show(childFragmentManager, bottomNavDrawerFragment.tag)
                true
            }
            R.id.menu_item_app_bar_search -> {
                activity?.let {
                    Snackbar.make(it.layout_root, "Text menu", Snackbar.LENGTH_SHORT)
                    .setAnchorView(it.fab)
                    .show() }
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

}
