package com.eugene_poroshin.money_manager.categories

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.categories.CategoriesFragment
import com.eugene_poroshin.money_manager.fragments.AddCategoryDialogFragment
import com.eugene_poroshin.money_manager.fragments.AddCategoryDialogFragment.EditNameDialogListener
import com.eugene_poroshin.money_manager.fragments.FragmentCommunicator
import com.eugene_poroshin.money_manager.fragments.OnFragmentActionListener
import com.eugene_poroshin.money_manager.repo.database.AccountEntity
import com.eugene_poroshin.money_manager.repo.database.CategoryEntity
import java.util.*

class CategoriesFragment : Fragment(), EditNameDialogListener {

    private var onFragmentActionListener: OnFragmentActionListener? = null
    private var toolbar: Toolbar? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: CategoriesAdapter? = null
    private var categories: List<CategoryEntity> = ArrayList()
    private var viewModel: CategoryViewModel? = null
    private var addCategoryDialogFragment: AddCategoryDialogFragment? = null
    private val communicator = object : FragmentCommunicator {
        override fun onItemClickListener(categoryName: String?) {}
        override fun onItemAccountClickListener(accountEntity: AccountEntity?) {}
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentActionListener) {
            onFragmentActionListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_categories, container, false)
        toolbar = view.findViewById(R.id.my_toolbar)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        recyclerView = view.findViewById(R.id.recycler_view_categories)
        return view
    }

    override fun onCreateOptionsMenu(
        menu: Menu,
        inflater: MenuInflater
    ) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.categories_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_add_category) {
            addCategoryDialogFragment = AddCategoryDialogFragment()
            addCategoryDialogFragment!!.setTargetFragment(this@CategoriesFragment, 1)
            addCategoryDialogFragment!!.show(parentFragmentManager, addCategoryDialogFragment!!::class.java.name)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onFinishEditDialog(inputText: String?) {
        if (inputText!= null) viewModel!!.insert(CategoryEntity(inputText))
        if (onFragmentActionListener != null) {
            onFragmentActionListener!!.onOpenCategoriesFragment()
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CategoriesAdapter(categories, communicator)
        recyclerView!!.layoutManager = LinearLayoutManager(activity)
        recyclerView!!.adapter = adapter
        viewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        viewModel!!.liveDataCategories.observe(
            viewLifecycleOwner,
            Observer { categoryEntities ->
                categories = categoryEntities
                adapter!!.setCategories(categories)
            })
    }

    override fun onDestroy() {
        super.onDestroy()
        onFragmentActionListener = null
    }

    companion object {
        fun newInstance(): CategoriesFragment {
            return CategoriesFragment()
        }
    }
}