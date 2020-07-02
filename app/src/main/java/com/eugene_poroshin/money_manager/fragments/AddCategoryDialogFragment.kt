package com.eugene_poroshin.money_manager.fragments

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.eugene_poroshin.money_manager.R

class AddCategoryDialogFragment : DialogFragment() {

    private var editText: EditText? = null

    interface EditNameDialogListener {
        fun onFinishEditDialog(inputText: String?)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_fragment_add_category, container, false)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder =
            AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        builder.setTitle("Добавление новой категории")
        builder.setView(inflater.inflate(R.layout.dialog_fragment_add_category, null))
            .setPositiveButton("Добавить") { _, _ -> sendBackResult() }
            .setNegativeButton("Отмена") { dialog, _ -> dialog?.dismiss() }
        return builder.create()
    }

    private fun sendBackResult() {
        editText = dialog!!.findViewById(R.id.edit_text_category_name)
        editText?.requestFocus()
        dialog!!.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE)
        val listener = targetFragment as EditNameDialogListener?
        listener!!.onFinishEditDialog(editText?.text.toString())
        dismiss()
    }
}