package com.orionst.notist.ui.screens.note

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.orionst.notist.R
import com.orionst.notist.common.getColorInt
import com.orionst.notist.data.entity.Note
import com.orionst.notist.ui.IWidgetTuning
import com.orionst.notist.ui.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_note.*
import java.util.*

class NoteFragment : BaseFragment<Note?, NoteViewState>(), IWidgetTuning {

    private var note: Note? = null
    private var noteId: String? = null
    override val viewModel: NoteViewModel by lazy {
        ViewModelProviders.of(this).get(NoteViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val safeArgs = NoteFragmentArgs.fromBundle(arguments!!)
        noteId = safeArgs.noteId
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layout = inflater.inflate(R.layout.fragment_note, container, false)

        val activity = requireActivity() as AppCompatActivity
        activity.supportActionBar?.setDisplayHomeAsUpEnabled(true);
        activity.supportActionBar?.setDisplayShowHomeEnabled(true);

        return layout
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bottomAppBarTune()

        noteId?.let {
            viewModel.loadNote(it)
        }

        titleNote.afterTextChanged { saveNote() }
        bodyNote.afterTextChanged { saveNote() }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                findNavController().popBackStack()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }

    override fun renderData(data: Note?) {
        this.note = data
        initViewData()
    }

    private fun initViewData() {
        note?.run {
            titleNote.setText(title)
            bodyNote.setText(text)
            bodyNote.setBackgroundColor(color.getColorInt(this@NoteFragment.requireContext()))
        }

    }

    override fun bottomAppBarTune() {
        activity?.let {
            //it.bottom_app_bar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
            it.bottom_app_bar.setNavigationIcon(R.drawable.ic_vector_back)
            it.fab.hide()

//            it.fab.setImageDrawable(context?.getDrawable(R.drawable.ic_vector_reply_white))
//            it.fab.setOnClickListener {
//                findNavController().popBackStack()
//            }
        }
    }

    private fun EditText.afterTextChanged(event: (String) -> Unit) {
        this.addTextChangedListener(object : TextWatcher {
            private var timer = Timer()
            override fun afterTextChanged(s: Editable?) {
                timer.cancel()
                timer = Timer()
                timer.schedule(object : TimerTask() {
                    override fun run() {
                        saveNote()
                    }
                }, SAVE_DELAY)

                saveNote()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
    }

    private fun saveNote() {
        if (titleNote.text.isNullOrBlank() || titleNote.text!!.length < 3) return

        note = note?.let {
            Note(
                id = it.id,
                title = titleNote.text.toString(),
                text = bodyNote.text.toString(),
                color = it.color,
                lastChanged = Date()
            )
        } ?: Note(
            UUID.randomUUID().toString(),
            titleNote.text.toString(),
            bodyNote.text.toString()
        )

        note?.let { viewModel.save(note!!) }
    }

    companion object {
        private const val DATE_FORMAT = "dd.MM.yy HH:mm"
        private const val SAVE_DELAY = 500L
    }
}