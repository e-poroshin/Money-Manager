package com.eugene_poroshin.money_manager.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.databinding.FragmentCategoriesBinding
import com.eugene_poroshin.money_manager.repo.database.CategoryEntity
import com.eugene_poroshin.money_manager.ui.OnFragmentActionListener
import org.koin.android.viewmodel.ext.android.viewModel

class CategoriesFragment : Fragment() {

    private var binding: FragmentCategoriesBinding? = null

    private val categoryViewModel: CategoryViewModel by viewModel()

    private val onFragmentActionListener: OnFragmentActionListener? get() = activity as? OnFragmentActionListener?
    private lateinit var categoriesAdapter: CategoriesAdapter
    private var addCategoryDialogFragment: AddCategoryDialogFragment? = null
    private val onItemClick = object : CategoriesAdapter.OnCategoryItemClick {
        override fun onItemClick(categoryName: String) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(REQUEST_KEY) { _, bundle ->
            val result = bundle.getString(BUNDLE_KEY)
            result?.let {
                categoryViewModel.insert(CategoryEntity(it))
                openCategoriesFragment()
            }
            onFragmentActionListener?.onOpenCategoriesFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_categories, container, false)
        return binding?.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        categoriesAdapter = CategoriesAdapter(onItemClick)
        binding?.recyclerViewCategories?.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = categoriesAdapter
        }
        categoryViewModel.liveDataCategories.observe(
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
        const val REQUEST_KEY = "requestKey"
        const val BUNDLE_KEY = "bundleKey"
    }
}