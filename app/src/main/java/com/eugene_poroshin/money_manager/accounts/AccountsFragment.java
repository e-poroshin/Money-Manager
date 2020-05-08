package com.eugene_poroshin.money_manager.accounts;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.eugene_poroshin.money_manager.AddAccountActivity;
import com.eugene_poroshin.money_manager.EditAccountActivity;
import com.eugene_poroshin.money_manager.R;
import com.eugene_poroshin.money_manager.fragments.FragmentCommunicator;
import com.eugene_poroshin.money_manager.repo.database.AccountEntity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class AccountsFragment extends Fragment {

    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private AccountsAdapter adapter;
    private List<AccountEntity> accounts = new ArrayList<AccountEntity>();
    private AccountsViewModel viewModel;
    private FragmentCommunicator communicator = new FragmentCommunicator() {
        @Override
        public void onItemClickListener(String categoryName) {
        }

        @Override
        public void onItemAccountClickListener(AccountEntity accountEntity) {
            Intent intent = new Intent(requireActivity(), EditAccountActivity.class);
            intent.putExtra(AccountEntity.class.getSimpleName(), (Serializable) accountEntity);
            startActivity(intent);
        }
    };

    public static AccountsFragment newInstance() {
        AccountsFragment fragment = new AccountsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_accounts, container, false);
        toolbar = view.findViewById(R.id.my_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        recyclerView = view.findViewById(R.id.recycler_view_accounts);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.accounts_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add) {
            Intent intent = new Intent(requireActivity(), AddAccountActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new AccountsAdapter(accounts, communicator);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        viewModel = new ViewModelProvider(this).get(AccountsViewModel.class);
        viewModel.getLiveDataAccounts().observe(getViewLifecycleOwner(), new Observer<List<AccountEntity>>() {
            @Override
            public void onChanged(List<AccountEntity> accountEntities) {
                accounts = accountEntities;
                adapter.setAccounts(accounts);
                toolbar.setTitle("Баланс: " + getBalance(accounts) + " BYN");
            }
        });
    }

    public double getBalance(List<AccountEntity> accounts) {
        double sum = 0;
        for (AccountEntity accountEntity : accounts) {
            sum += accountEntity.getBalance();
        }
        return sum;
    }
}
