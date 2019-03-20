package com.orionst.notist.ui.screens.note_list

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.orionst.notist.R

class LogoutDialog : DialogFragment() {

    var listener: LogoutListener? = null

    companion object {
        val TAG = LogoutDialog::class.java.name + "TAG"
        fun createInstance(listener: LogoutListener? = null): LogoutDialog {
            val dialog = LogoutDialog()
            dialog.listener = listener
            return dialog
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(context)
            .setTitle(R.string.logout_dialog_title)
            .setMessage(R.string.logout_dialog_message)
            .setPositiveButton(R.string.ok_btn_title) { _, _ ->  listener?.onLogout() }
            .setNegativeButton(R.string.logout_dialog_cancel) { _, _ -> dismiss() }
            .create()

    interface LogoutListener {
        fun onLogout()
    }
}