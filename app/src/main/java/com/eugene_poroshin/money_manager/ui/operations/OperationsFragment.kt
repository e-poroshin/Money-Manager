package com.eugene_poroshin.money_manager.ui.operations

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.databinding.FragmentOperationsBinding
import com.eugene_poroshin.money_manager.di.App
import com.eugene_poroshin.money_manager.repo.database.AccountEntity
import com.eugene_poroshin.money_manager.repo.database.Operation
import com.eugene_poroshin.money_manager.repo.viewmodel.OperationsViewModel
import com.eugene_poroshin.money_manager.ui.FragmentCommunicator
import javax.inject.Inject

class OperationsFragment : Fragment(R.layout.fragment_operations) {

    private var binding: FragmentOperationsBinding? = null

    @Inject
    lateinit var viewModel: OperationsViewModel
    
    private var operations: List<Operation> = emptyList()
        set(value) {
            field = value
            updateOperationList(value)
        }
    private val communicator = object : FragmentCommunicator {
        override fun onItemClickListener(categoryName: String?) {}
        override fun onItemAccountClickListener(accountEntity: AccountEntity?) {}
    }
    private val adapter: OperationsAdapter = OperationsAdapter(operations, communicator)

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.fragmentSubComponentBuilder().with(this).build().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentOperationsBinding.bind(view)
        binding?.fabAddOperation?.setOnClickListener {
            startActivity(Intent(requireActivity(), AddOperationActivity::class.java))
        }
        binding?.recyclerViewOperations?.layoutManager = LinearLayoutManager(activity)
        binding?.recyclerViewOperations?.adapter = adapter
        viewModel.liveDataOperations.observe(viewLifecycleOwner, { operations ->
            this.operations = operations
        })
    }

    private fun updateOperationList(operations: List<Operation>) {
        adapter.setOperations(operations)
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