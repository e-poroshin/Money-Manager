package com.eugene_poroshin.money_manager.ui.statistics

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.databinding.FragmentStatisticsBinding
import com.eugene_poroshin.money_manager.di.App
import com.eugene_poroshin.money_manager.repo.database.CategoryEntity
import com.eugene_poroshin.money_manager.repo.database.Operation
import com.eugene_poroshin.money_manager.repo.viewmodel.CategoryViewModel
import com.eugene_poroshin.money_manager.repo.viewmodel.OperationsViewModel
import com.eugene_poroshin.money_manager.ui.operations.OperationType
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import java.util.*
import javax.inject.Inject

class StatisticsFragment : Fragment(R.layout.fragment_statistics) {

    @Inject
    lateinit var viewModelCategory: CategoryViewModel

    @Inject
    lateinit var viewModelOperation: OperationsViewModel

    private var binding: FragmentStatisticsBinding? = null
    private var operations: List<Operation> = emptyList()
    private var categories: List<CategoryEntity> = emptyList()
        set(value) {
            field = value
            setUpPieChart(operations, field)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.fragmentSubComponentBuilder().with(this).build().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStatisticsBinding.bind(view)
        viewModelOperation.liveDataOperations.observe(
            viewLifecycleOwner,
            { operationList ->
                operations = operationList
            })
        viewModelCategory.liveDataCategories.observe(
            viewLifecycleOwner,
            { categoryList ->
                categories = categoryList
            })
    }

    private fun setUpPieChart(
        operationList: List<Operation>,
        categoryList: List<CategoryEntity>
    ) {
        //todo kotlin filter - is it ok? \/ groupBy???

        val pieEntries: MutableList<PieEntry> = ArrayList()
        val categoryIDs = categoryList.map { categories -> categories.id }
        categoryIDs.forEach { outerLoopCategoryId ->
            var sum = 0.0
            var label: String? = ""
	    operationList
	        .filter { it.operationEntity?.type == OperationType.EXPENSE }
	        .filter { it.operationEntity?.categoryId == outerLoopCategoryId }
	        .forEach {
                sum += it.operationEntity?.sum ?: 0.0
                label = it.category?.name
            }
            if (sum != 0.0) {
                pieEntries.add(PieEntry(sum.toFloat(), label))
            }
        }
        val dataSet = PieDataSet(pieEntries, "")
        dataSet.setColors(*ChartColors.MATERIAL_COLORS)
        dataSet.valueTextColor = R.color.colorPrimaryDark
        dataSet.valueTextSize = 14f
        val data = PieData(dataSet)
        binding?.pieChart?.data = data
        binding?.pieChart?.animateXY(1000, 1000)
        binding?.pieChart?.invalidate()
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {       
        fun getInstance(): StatisticsFragment = StatisticsFragment()
    }
}