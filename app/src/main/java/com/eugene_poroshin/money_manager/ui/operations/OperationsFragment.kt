package com.eugene_poroshin.money_manager.ui.operations

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.databinding.FragmentOperationsBinding
import com.eugene_poroshin.money_manager.repo.database.Operation
import org.koin.android.viewmodel.ext.android.viewModel

class OperationsFragment : Fragment() {

    private var binding: FragmentOperationsBinding? = null

    private val operationsViewModel: OperationsViewModel by viewModel()

    private val operationsAdapter: OperationsAdapter = OperationsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_operations, container, false)
        return binding?.root
    }

    override fun onViewCreated(
            view: View,
            savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding?.fabAddOperation?.setOnClickListener {
            startActivity(Intent(requireActivity(), AddOperationActivity::class.java))
        }
        binding?.recyclerViewOperations?.layoutManager = LinearLayoutManager(activity)
        binding?.recyclerViewOperations?.adapter = operationsAdapter
        operationsViewModel.liveDataOperations.observe(viewLifecycleOwner, { operations ->
            operationsAdapter.operations = operations
            updateOperationList(operations)
        })
    }

    private fun updateOperationList(operations: List<Operation>) {
        binding?.textViewPressPlus?.visibility = if (operations.isEmpty()) {
            View.VISIBLE
        } else {
            View.GONE
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {
        fun getInstance(): OperationsFragment = OperationsFragment()
    }
}