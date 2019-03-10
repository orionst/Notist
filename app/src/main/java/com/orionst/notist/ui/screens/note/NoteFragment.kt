package com.orionst.notist.ui.screens.note

import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomappbar.BottomAppBar
import com.orionst.notist.R
import com.orionst.notist.data.entity.Note
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_note.*
import java.util.*

class NoteFragment : Fragment() {

    private var note: Note? = null
    lateinit var viewModel: NoteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val safeArgs = NoteFragmentArgs.fromBundle(arguments!!)
        note = safeArgs.note

        viewModel = ViewModelProviders.of(this).get(NoteViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_note, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
    }

    private fun initUI() {

        note?.let {
            titleNote.setText(it.title)
            bodyNote.setText(it.text)
        }

        activity?.let {
            it.bottom_app_bar.navigationIcon = null
            it.bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            it.bottom_app_bar.replaceMenu(R.menu.note_menu)

            it.fab.setImageDrawable(context?.getDrawable(R.drawable.ic_vector_reply_white))
            it.fab.setOnClickListener {
                findNavController().popBackStack()
            }
        }

        titleNote.afterTextChanged { saveNote()  }
        bodyNote.afterTextChanged { saveNote() }

    }

    private fun EditText.afterTextChanged(event: (String) -> Unit) {
        this.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                event.invoke(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) { }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) { }
        })
    }

    private fun saveNote() {
        if (titleNote.text.isNullOrBlank() || titleNote.text!!.length < 3) return

        Handler().postDelayed({
            note = note?.let {
                Note(
                    id = it.id,
                    title = titleNote.text.toString(),
                    text = bodyNote.text.toString(),
                    lastChanged = Date()
                )
            } ?: Note(
                UUID.randomUUID().toString(),
                titleNote.text.toString(),
                bodyNote.text.toString()
            )

            note?.let { viewModel.save(note!!) }

        }, SAVE_DELAY)
    }

    companion object {
        private const val DATE_FORMAT = "dd.MM.yy HH:mm"
        private const val SAVE_DELAY = 500L
    }
}