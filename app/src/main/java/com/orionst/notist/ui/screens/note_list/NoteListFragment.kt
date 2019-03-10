package com.orionst.notist.ui.screens.note_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.bottomappbar.BottomAppBar
import com.orionst.notist.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_note_list.*


class NoteListFragment : Fragment() {

    private lateinit var viewModel: NoteListViewModel
    private lateinit var adapter: NotesRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_note_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {
        rv_notes.layoutManager = GridLayoutManager(context, 2)
        adapter = NotesRecyclerViewAdapter()
        rv_notes.adapter = adapter

        activity?.let {
            it.bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
            it.bottom_app_bar.setNavigationIcon(R.drawable.ic_vector_menu_white)
            it.bottom_app_bar.replaceMenu(R.menu.main_bottomappbar_menu)

            it.fab.setImageDrawable(context?.getDrawable(R.drawable.ic_vector_add))
            it.fab.setOnClickListener {
                val action = NoteListFragmentDirections.actionOpenNote()
                findNavController().navigate(action)
            }
        }

        viewModel = ViewModelProviders.of(this).get(NoteListViewModel::class.java)
        viewModel.viewState().observe(this, Observer<NotesViewState> { state ->
            state?.let {
                adapter.notes = it.notes
            }
        })


    }
}
