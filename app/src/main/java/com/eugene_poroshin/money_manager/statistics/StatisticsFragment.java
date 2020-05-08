package com.eugene_poroshin.money_manager.statistics;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.eugene_poroshin.money_manager.R;
import com.eugene_poroshin.money_manager.categories.CategoryViewModel;
import com.eugene_poroshin.money_manager.fragments.OnFragmentActionListener;
import com.eugene_poroshin.money_manager.operations.OperationType;
import com.eugene_poroshin.money_manager.operations.OperationsViewModel;
import com.eugene_poroshin.money_manager.repo.database.Operation;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class StatisticsFragment extends Fragment {

    private OnFragmentActionListener onFragmentActionListener;
    private Toolbar toolbar;
    private PieChart pieChart;
    private List<Operation> operations = new ArrayList<>();
    private OperationsViewModel viewModelOperation;
    private CategoryViewModel viewModelCategory;
    private List<String> categoryNames = new ArrayList<>();

    public static StatisticsFragment newInstance() {
        StatisticsFragment fragment = new StatisticsFragment();
        return fragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentActionListener) {
            onFragmentActionListener = (OnFragmentActionListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        toolbar = view.findViewById(R.id.my_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        pieChart = view.findViewById(R.id.pieChart);

        viewModelOperation = new ViewModelProvider(this).get(OperationsViewModel.class);
        viewModelCategory = new ViewModelProvider(this).get(CategoryViewModel.class);
        viewModelOperation.getLiveData().observe(getViewLifecycleOwner(), new Observer<List<Operation>>() {
            @Override
            public void onChanged(List<Operation> operationList) {
                operations = operationList;
                viewModelCategory.getLiveDataCategoryNames().observe(getViewLifecycleOwner(), new Observer<List<String>>() {
                    @Override
                    public void onChanged(List<String> categories) {
                        categoryNames = categories;
                        setUpPieChart(operations, categoryNames);
                    }
                });
            }
        });
        return view;
    }

    private void setUpPieChart(List<Operation> operationList, List<String> categoryNames) {
        List<PieEntry> pieEntries = new ArrayList<>();

        for (int j = 0; j < categoryNames.size(); j++) {
            double sum = 0;
            String label = "";
            for (int i = 0; i < operationList.size(); i++) {
                if (operationList.get(i).getOperationEntity().getType().equals(OperationType.CONSUMPTION)) {
                    if (categoryNames.get(j).equals(operationList.get(i).getCategory().getName())) {
                        sum += operationList.get(i).getOperationEntity().getSum();
                        label = operationList.get(i).getCategory().getName();
                    }
                }
            }
            if (sum != 0) {
                pieEntries.add(new PieEntry((float) sum, label));
            }
        }

        PieDataSet dataSet = new PieDataSet(pieEntries, "");
        dataSet.setColors(ChartColors.MATERIAL_COLORS);
        dataSet.setValueTextColor(R.color.colorPrimaryDark);
        dataSet.setValueTextSize(14);
        PieData data = new PieData(dataSet);

        pieChart.setData(data);
        pieChart.animateXY(1000, 1000);
        pieChart.invalidate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onFragmentActionListener = null;
    }
}
