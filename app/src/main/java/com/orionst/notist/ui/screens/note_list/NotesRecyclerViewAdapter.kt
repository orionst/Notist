package com.orionst.notist.ui.screens.note_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.orionst.notist.R
import com.orionst.notist.data.entity.Note
import kotlinx.android.synthetic.main.item_note.view.*

class NotesRecyclerViewAdapter : RecyclerView.Adapter<NotesRecyclerViewAdapter.ViewHolder>() {

    var notes: List<Note> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_note,
                parent,
                false
            )
        )

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(notes[position])


    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(note: Note) = with(itemView) {
            tv_title.text = note.title
            tv_text.text = note.text

            itemView.setOnClickListener {
                val action = NoteListFragmentDirections.actionOpenNote()
                action.note = note
                findNavController().navigate(action)
            }
        }

    }
}