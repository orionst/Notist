package com.orionst.notist.ui.screens.note_list

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.orionst.notist.R
import com.orionst.notist.ui.NotesViewState


class NoteListFragment : Fragment() {

    private lateinit var viewModel: NoteListViewModel
    private lateinit var adapter: NotesRecyclerViewAdapter

    companion object {
        fun newInstance() = NoteListFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.note_list_fragment, container, false)

        val rvNotes = layout.findViewById<RecyclerView>(R.id.rv_notes)
        rvNotes.layoutManager = GridLayoutManager(context, 2)
        adapter = NotesRecyclerViewAdapter()
        rvNotes.adapter = adapter

        return layout
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(NoteListViewModel::class.java)

        viewModel.viewState().observe(this, Observer<NotesViewState> { t ->
            t?.let { adapter.notes = it.notes }
        })
    }

}
