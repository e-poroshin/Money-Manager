package com.eugene_poroshin.money_manager.ui.categories

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.databinding.FragmentCategoriesBinding
import com.eugene_poroshin.money_manager.di.App
import com.eugene_poroshin.money_manager.repo.database.CategoryEntity
import com.eugene_poroshin.money_manager.ui.OnFragmentActionListener
import java.util.*
import javax.inject.Inject

class CategoriesFragment : Fragment(R.layout.fragment_categories) {

    private var binding: FragmentCategoriesBinding? = null

    @Inject
    lateinit var viewModel: CategoryViewModel

    private val onFragmentActionListener: OnFragmentActionListener? get() = activity as? OnFragmentActionListener?
    private lateinit var categoriesAdapter: CategoriesAdapter
    private var categories: List<CategoryEntity> = ArrayList()
    private var addCategoryDialogFragment: AddCategoryDialogFragment? = null
    private val communicator = object : CategoriesAdapter.OnCategoryItemClick {
        override fun onItemClick(categoryName: String) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.fragmentSubComponentBuilder().with(this).build().inject(this)
        super.onCreate(savedInstanceState)
        setFragmentResultListener("requestKey") { _, bundle ->
            val result = bundle.getString("bundleKey")
            result?.let {
                viewModel.insert(CategoryEntity(it))
                openCategoriesFragment()
            }
            onFragmentActionListener?.onOpenCategoriesFragment()
        }
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCategoriesBinding.bind(view)
        initToolbar()
        categoriesAdapter = CategoriesAdapter(communicator)
        binding?.recyclerViewCategories?.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = categoriesAdapter
        }
        viewModel.liveDataCategories.observe(
            viewLifecycleOwner,
            { categoryEntities ->
                categoriesAdapter.categories = categoryEntities
            })
    }

    private fun initToolbar() {
        binding?.myToolbar?.apply {
            inflateMenu(R.menu.categories_menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_add_category -> showAddDialogFragment()
                }
                true
            }
        }
    }

    private fun showAddDialogFragment() {
        addCategoryDialogFragment = AddCategoryDialogFragment()
        addCategoryDialogFragment!!.show(
            parentFragmentManager,
            addCategoryDialogFragment!!::class.java.name
        )
    }

    private fun openCategoriesFragment() {
        requireActivity().supportFragmentManager
            .beginTransaction()
            .replace(
                R.id.fragmentContainer,
                getInstance(),
                getInstance()::class.java.simpleName
            )
            .commit()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {
        fun getInstance(): CategoriesFragment = CategoriesFragment()
    }
}