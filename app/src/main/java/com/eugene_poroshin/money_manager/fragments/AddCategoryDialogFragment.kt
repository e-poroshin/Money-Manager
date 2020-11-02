package com.eugene_poroshin.money_manager.fragments

import android.app.Dialog
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.eugene_poroshin.money_manager.R

class AddCategoryDialogFragment : DialogFragment(R.layout.dialog_fragment_add_category) {

    private var editText: EditText? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Добавление новой категории")
        builder.setView(requireActivity().layoutInflater.inflate(R.layout.dialog_fragment_add_category, null))
            .setPositiveButton("Добавить") { _, _ -> sendBackResult() }
            .setNegativeButton("Отмена", null)
        return builder.create()
    }

    private fun sendBackResult() {
        //todo Fragment Result Listener - is it ok?
        editText = dialog?.findViewById(R.id.edit_text_category_name)
        val result = editText?.text?.toString()?.capitalize()
        when (result) {
            "" -> Toast.makeText(context, "Введите название категории", Toast.LENGTH_SHORT).show()
            null -> Toast.makeText(context, "Введите название категории", Toast.LENGTH_SHORT).show()
            else -> {
                setFragmentResult("requestKey", bundleOf("bundleKey" to result))
                dismiss()
            }
        }
    }

    override fun onDestroyView() {
        editText = null
        super.onDestroyView()
    }
}