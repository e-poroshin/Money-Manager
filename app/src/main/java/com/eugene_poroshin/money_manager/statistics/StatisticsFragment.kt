package com.eugene_poroshin.money_manager.statistics

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.categories.CategoryViewModel
import com.eugene_poroshin.money_manager.databinding.FragmentStatisticsBinding
import com.eugene_poroshin.money_manager.di.App
import com.eugene_poroshin.money_manager.operations.OperationType
import com.eugene_poroshin.money_manager.operations.OperationsViewModel
import com.eugene_poroshin.money_manager.repo.database.Operation
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
    private var operations: List<Operation> = ArrayList()
    private var categoryNames: List<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.fragmentSubComponentBuilder().with(this).build().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentStatisticsBinding.bind(view)
        viewModelOperation.liveData.observe(
            viewLifecycleOwner,
            { operationList ->
                operations = operationList
            })
        viewModelCategory.liveDataCategoryNames.observe(
            viewLifecycleOwner,
            { categories ->
                categoryNames = categories
                setUpPieChart(operations, categoryNames)
            })
    }

    private fun setUpPieChart(
        operationList: List<Operation>,
        categoryNames: List<String>
    ) {
        //todo kotlin filter - is it ok? \/

        val pieEntries: MutableList<PieEntry> = ArrayList()
        categoryNames.forEach { outerLoopCategoryName ->
            var sum = 0.0
            var label: String? = ""

            val expenseOperationsList =
                operationList.filter { it.operationEntity?.type == OperationType.EXPENSE }
            val resultOperationsList =
                expenseOperationsList.filter { it.category?.name == outerLoopCategoryName }
            resultOperationsList.forEach {
                sum += it.operationEntity?.sum!!
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
        private var INSTANCE: StatisticsFragment? = null

        //todo is it ok, or by lazy?
        fun getInstance(): StatisticsFragment {
            return if (INSTANCE == null) {
                INSTANCE = StatisticsFragment()
                INSTANCE!!
            } else INSTANCE!!
        }
    }
}