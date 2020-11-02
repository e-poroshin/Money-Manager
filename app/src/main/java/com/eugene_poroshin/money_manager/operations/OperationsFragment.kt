package com.eugene_poroshin.money_manager.operations

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.eugene_poroshin.money_manager.AddOperationActivity
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.databinding.FragmentOperationsBinding
import com.eugene_poroshin.money_manager.di.App
import com.eugene_poroshin.money_manager.fragments.FragmentCommunicator
import com.eugene_poroshin.money_manager.repo.database.AccountEntity
import com.eugene_poroshin.money_manager.repo.database.Operation
import com.eugene_poroshin.money_manager.statistics.StatisticsFragment
import java.util.*
import javax.inject.Inject

class OperationsFragment : Fragment(R.layout.fragment_operations) {

    private var binding: FragmentOperationsBinding? = null

    @Inject
    lateinit var viewModel: OperationsViewModel

    private lateinit var adapter: OperationsAdapter
    private var operations: List<Operation> = ArrayList()
    private val communicator = object : FragmentCommunicator {
        override fun onItemClickListener(categoryName: String?) {}
        override fun onItemAccountClickListener(accountEntity: AccountEntity?) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.fragmentSubComponentBuilder().with(this).build().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        //todo bind or inflate \/
        binding = FragmentOperationsBinding.bind(view)
        binding!!.fabAddOperation.setOnClickListener {
            startActivity(Intent(requireActivity(), AddOperationActivity::class.java))
        }
        adapter = OperationsAdapter(operations, communicator)
        binding!!.recyclerViewOperations.layoutManager = LinearLayoutManager(activity)
        binding!!.recyclerViewOperations.adapter = adapter
        viewModel.liveData.observe(viewLifecycleOwner, { operations ->
            this.operations = operations
            operations?.let { adapter.setOperations(it) }
            if (operations.isNotEmpty()) binding!!.textViewPressPlus.visibility = View.GONE
        })
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {
        private var INSTANCE: OperationsFragment? = null

        fun getInstance(): OperationsFragment {
            return if(INSTANCE == null) {
                INSTANCE = OperationsFragment()
                INSTANCE!!
            } else INSTANCE!!
        }
    }
}