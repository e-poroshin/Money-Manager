package com.eugene_poroshin.money_manager.ui.categories

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.databinding.DialogFragmentAddCategoryBinding
import java.util.*

class AddCategoryDialogFragment : DialogFragment(R.layout.dialog_fragment_add_category) {

    private var binding: DialogFragmentAddCategoryBinding? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogFragmentAddCategoryBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(requireActivity())
            .setTitle(getString(R.string.dialog_add_category_title))
            .setView(binding?.root)
            .setPositiveButton(getString(R.string.all_add)) { _, _ -> sendBackResult() }
            .setNegativeButton(getString(R.string.all_cancel), null)
        return builder.create()
    }

    private fun sendBackResult() {
        val result = binding?.editTextCategoryName?.text?.toString()
            ?.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        if (result.isNullOrBlank()) {
            Toast.makeText(context, getString(R.string.enter_category_name), Toast.LENGTH_SHORT).show()
        } else {
            setFragmentResult("requestKey", bundleOf("bundleKey" to result))
            dismiss()
        }
        //todo выносим текст в константы - requestKey, bundleKey
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}