package com.eugene_poroshin.money_manager.operations

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eugene_poroshin.money_manager.AddOperationActivity
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.fragments.FragmentCommunicator
import com.eugene_poroshin.money_manager.operations.OperationsFragment
import com.eugene_poroshin.money_manager.repo.database.AccountEntity
import com.eugene_poroshin.money_manager.repo.database.Operation
import java.util.*

class OperationsFragment : Fragment() {

    private var toolbar: Toolbar? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: OperationsAdapter? = null
    private var operations: List<Operation> = ArrayList()
    private lateinit var viewModel: OperationsViewModel
    private var textViewPressPlus: TextView? = null
    private val communicator = object : FragmentCommunicator {
        override fun onItemClickListener(categoryName: String?) {}
        override fun onItemAccountClickListener(accountEntity: AccountEntity?) {}
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =
            inflater.inflate(R.layout.fragment_operations, container, false)
        toolbar = view.findViewById(R.id.my_toolbar)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        view.findViewById<View>(R.id.fabAddOperation)
            .setOnClickListener {
                val intent = Intent(requireActivity(), AddOperationActivity::class.java)
                startActivity(intent)
            }
        recyclerView = view.findViewById(R.id.recycler_view_operations)
        textViewPressPlus = view.findViewById(R.id.textViewPressPlus)
        return view
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        adapter = OperationsAdapter(operations, communicator)
        recyclerView!!.layoutManager = LinearLayoutManager(activity)
        recyclerView!!.adapter = adapter
        viewModel = ViewModelProvider(this).get(OperationsViewModel::class.java)
        viewModel.liveData.observe(viewLifecycleOwner, Observer { operations ->
                this@OperationsFragment.operations = operations
                operations?.let { adapter!!.setOperations(it) }
                if (operations.isNotEmpty()) textViewPressPlus!!.visibility = View.GONE
            })
    }

    companion object {
        fun newInstance(): OperationsFragment {
            return OperationsFragment()
        }
    }
}