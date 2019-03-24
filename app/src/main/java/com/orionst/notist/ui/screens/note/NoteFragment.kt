package com.orionst.notist.ui.screens.note

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import com.orionst.notist.R
import com.orionst.notist.common.getColorInt
import com.orionst.notist.data.entity.Note
import com.orionst.notist.ui.IWidgetTuning
import com.orionst.notist.ui.base.BaseFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_note.*
import org.jetbrains.anko.support.v4.alert
import org.koin.android.viewmodel.ext.android.viewModel
import java.util.*

class NoteFragment : BaseFragment<NoteViewState.Data, NoteViewState>(), IWidgetTuning {

    private var note: Note? = null
    private var noteId: String? = null
    private var color = Note.Color.WHITE

    override val model: NoteViewModel by viewModel()
//    override val model: NoteViewModel by lazy {
//        ViewModelProviders.of(this).get(NoteViewModel::class.java)
//    }

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

        colorPicker.onColorClickListener =  {
            setViewColor(it)
            color = it
            saveNote()
        }

        noteId?.let {
            model.loadNote(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) = inflater.inflate(R.menu.note_menu, menu)

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> findNavController().popBackStack()
            R.id.palette -> togglePalette()
            R.id.delete -> deleteNote()
        }
        return  super.onOptionsItemSelected(item)
    }

    override fun renderData(data: NoteViewState.Data) {
        if (data.isDeleted) {
            findNavController().popBackStack()
        }

        this.note = data.note
        initViewData()
    }

    private fun initViewData() {
        note?.run {
            removeEditListener()

            titleNote.setText(title)
            bodyNote.setText(text)
            setViewColor(color)

            setEditListener()
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

    private val textChangeWatcher = object : TextWatcher {
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
    }

    private fun setViewColor(color: Note.Color) {
        color_divider.setBackgroundColor(color.getColorInt(this@NoteFragment.requireContext()))
    }

    private fun togglePalette() {
        if (colorPicker.isOpen) {
            colorPicker.close()
        } else {
            colorPicker.open()
        }
    }

    private fun saveNote() {
        //TODO After quick click back button note can be null
        if (titleNote.text.isNullOrBlank() || titleNote.text!!.length < 3) return

        note = note?.let {
            Note(
                id = it.id,
                title = titleNote.text.toString(),
                text = bodyNote.text.toString(),
                color = color,
                lastChanged = Date()
            )
        } ?: Note(
            UUID.randomUUID().toString(),
            titleNote.text.toString(),
            bodyNote.text.toString()
        )

        note?.let { model.save(note!!) }
    }

    private fun deleteNote() {
        alert {
            messageResource = R.string.delete_dialog_message
            negativeButton(R.string.delete_dialog_cancel) { dialog -> dialog.dismiss() }
            positiveButton(R.string.delete_dialog_submit) { model.deleteNote()}
        }.show()
    }

    private fun setEditListener() {
        titleNote.addTextChangedListener(textChangeWatcher)
        bodyNote.addTextChangedListener(textChangeWatcher)
    }

    private fun removeEditListener() {
        titleNote.removeTextChangedListener(textChangeWatcher)
        bodyNote.removeTextChangedListener(textChangeWatcher)
    }



    companion object {
        private const val DATE_FORMAT = "dd.MM.yy HH:mm"
        private const val SAVE_DELAY = 500L
    }
}