package com.eugene_poroshin.money_manager.ui.categories

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.databinding.DialogFragmentAddCategoryBinding
import com.eugene_poroshin.money_manager.ui.categories.CategoriesFragment.Companion.BUNDLE_KEY
import com.eugene_poroshin.money_manager.ui.categories.CategoriesFragment.Companion.REQUEST_KEY
import java.util.*

class AddCategoryDialogFragment : DialogFragment() {

    private var binding: DialogFragmentAddCategoryBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_fragment_add_category, container, false)
        return binding?.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
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
            setFragmentResult(REQUEST_KEY, bundleOf(BUNDLE_KEY to result))
            dismiss()
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }
}