package com.eugene_poroshin.money_manager.operations;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eugene_poroshin.money_manager.AddOperationActivity;
import com.eugene_poroshin.money_manager.R;
import com.eugene_poroshin.money_manager.fragments.FragmentCommunicator;
import com.eugene_poroshin.money_manager.repo.database.AccountEntity;
import com.eugene_poroshin.money_manager.repo.database.Operation;

import java.util.ArrayList;
import java.util.List;

public class OperationsFragment extends Fragment {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private OperationsAdapter adapter;
    private List<Operation> operations = new ArrayList<>();
    private OperationsViewModel viewModel;
    private TextView textViewPressPlus;
    private FragmentCommunicator communicator = new FragmentCommunicator() {
        @Override
        public void onItemClickListener(String categoryName) {
        }

        @Override
        public void onItemAccountClickListener(AccountEntity accountEntity) {
        }
    };

    public static OperationsFragment newInstance() {
        OperationsFragment fragment = new OperationsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_operations, container, false);
        toolbar = view.findViewById(R.id.my_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        view.findViewById(R.id.fabAddOperation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), AddOperationActivity.class);
                startActivity(intent);
            }
        });
        recyclerView = view.findViewById(R.id.recycler_view_operations);
        textViewPressPlus = view.findViewById(R.id.textViewPressPlus);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new OperationsAdapter(operations, communicator);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        viewModel = new ViewModelProvider(this).get(OperationsViewModel.class);
        viewModel.getLiveData().observe(getViewLifecycleOwner(), new Observer<List<Operation>>() {

            @Override
            public void onChanged(List<Operation> operations) {
                OperationsFragment.this.operations = operations;
                adapter.setOperations(operations);
                if (!operations.isEmpty()) textViewPressPlus.setVisibility(View.GONE);
            }
        });
    }
}
