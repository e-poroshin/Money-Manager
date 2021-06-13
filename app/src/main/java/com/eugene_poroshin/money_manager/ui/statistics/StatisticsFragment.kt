package com.eugene_poroshin.money_manager.ui.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.databinding.FragmentStatisticsBinding
import com.eugene_poroshin.money_manager.repo.database.Operation
import com.eugene_poroshin.money_manager.ui.operations.OperationType
import com.eugene_poroshin.money_manager.ui.operations.OperationsViewModel
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import org.koin.android.viewmodel.ext.android.viewModel

class StatisticsFragment : Fragment() {

    private val viewModelOperation: OperationsViewModel by viewModel()

    private var binding: FragmentStatisticsBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_statistics, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModelOperation.liveDataOperations.observe(
                viewLifecycleOwner,
                { operationList ->
                    setUpPieChart(operationList)
                })
    }

    private fun setUpPieChart(operationList: List<Operation>) {
        val pieEntries = operationList
                .filter { it.operationEntity.type == OperationType.EXPENSE }
                .groupBy { it.category.name }
                .map { (categoryName, operations) ->
                    val sum = operations.sumByDouble { it.operationEntity.sum }
                    PieEntry(sum.toFloat(), categoryName)
                }
                .filter { pieEntry -> pieEntry.value > 0 }

        val dataSet = PieDataSet(pieEntries, "").apply {
            setColors(*ChartColors.MATERIAL_COLORS)
            valueTextColor = R.color.colorPrimaryDark
            valueTextSize = 14f
        }

        val data = PieData(dataSet)
        binding?.pieChart?.apply {
            this.data = data
            animateXY(1000, 1000)
            invalidate()
        }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {
        fun getInstance(): StatisticsFragment = StatisticsFragment()
    }
}