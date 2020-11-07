package com.eugene_poroshin.money_manager.ui.categories

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.databinding.DialogFragmentAddCategoryBinding

class AddCategoryDialogFragment : DialogFragment(R.layout.dialog_fragment_add_category) {

    private var binding: DialogFragmentAddCategoryBinding? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        //todo don't work \/
        binding = DialogFragmentAddCategoryBinding.bind(requireActivity().findViewById(R.id.dialog_fragment_add_category_root))
        val builder = AlertDialog.Builder(requireActivity())
        builder.setTitle("Добавление новой категории")
        builder.setView(binding!!.root)
            .setPositiveButton("Добавить") { _, _ -> sendBackResult() }
            .setNegativeButton("Отмена", null)
        return builder.create()
    }

    private fun sendBackResult() {
        //todo Fragment Result Listener - is it ok?
        val result = binding?.editTextCategoryName?.text?.toString()?.capitalize()
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
        binding = null
        super.onDestroyView()
    }
}