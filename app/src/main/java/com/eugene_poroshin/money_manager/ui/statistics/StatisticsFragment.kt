package com.eugene_poroshin.money_manager.ui.statistics

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.databinding.FragmentStatisticsBinding
import com.eugene_poroshin.money_manager.di.App
import com.eugene_poroshin.money_manager.repo.database.Operation
import com.eugene_poroshin.money_manager.repo.viewmodel.OperationsViewModel
import com.eugene_poroshin.money_manager.ui.operations.OperationType
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import javax.inject.Inject

class StatisticsFragment : Fragment(R.layout.fragment_statistics) {

    @Inject
    lateinit var viewModelOperation: OperationsViewModel

    private var binding: FragmentStatisticsBinding? = null

    private var operations: List<Operation> = emptyList()
        set(value) {
            field = value
            setUpPieChart(field)
        }
    //Зачем так сложно? Мы, вроде ни где не используем состояние operations и все что делаем, это
    //присваиваем значение, после чего вызывается setUpPieChart(), правда ли нам нужно это поле?

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
    }

    private fun setUpPieChart(
            operationList: List<Operation>
    ) {
        //хорошо бы иметь единый кодстайл для выравниваний
        val pieEntries = operationList
                .filter { it.operationEntity?.type == OperationType.EXPENSE }
                .groupBy { it.category?.name }
                .map { (categoryName, operations) ->
                    val sum = operations.sumByDouble { it.operationEntity?.sum!! }
                    PieEntry(sum.toFloat(), categoryName)
                }
                .filter { pieEntry -> pieEntry.value > 0 }

        val dataSet = PieDataSet(pieEntries, "")
        dataSet.setColors(*ChartColors.MATERIAL_COLORS)
        dataSet.valueTextColor = R.color.colorPrimaryDark
        dataSet.valueTextSize = 14f
        //можно немного уменьшить код с помощу PieDataSet(pieEntries, "").apply {...
        val data = PieData(dataSet)
        binding?.pieChart?.data = data
        binding?.pieChart?.animateXY(1000, 1000)
        binding?.pieChart?.invalidate()
        //убрать копипасту binding?.pieChart? с помощью apply
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {
        fun getInstance(): StatisticsFragment = StatisticsFragment()
    }
}