package com.eugene_poroshin.money_manager.categories;

import android.content.Context;
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

import com.eugene_poroshin.money_manager.R;
import com.eugene_poroshin.money_manager.fragments.AddCategoryDialogFragment;
import com.eugene_poroshin.money_manager.fragments.FragmentCommunicator;
import com.eugene_poroshin.money_manager.fragments.OnFragmentActionListener;
import com.eugene_poroshin.money_manager.repo.database.AccountEntity;
import com.eugene_poroshin.money_manager.repo.database.CategoryEntity;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment implements AddCategoryDialogFragment.EditNameDialogListener {

    private OnFragmentActionListener onFragmentActionListener;
    private Toolbar toolbar;
    private RecyclerView recyclerView;
    private CategoriesAdapter adapter;
    private List<CategoryEntity> categories = new ArrayList<>();
    private CategoryViewModel viewModel;
    private AddCategoryDialogFragment addCategoryDialogFragment;
    private FragmentCommunicator communicator = new FragmentCommunicator() {
        @Override
        public void onItemClickListener(String categoryName) {
        }
        @Override
        public void onItemAccountClickListener(AccountEntity accountEntity) {
        }
    };

    public static CategoriesFragment newInstance() {
        CategoriesFragment fragment = new CategoriesFragment();
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
        View view = inflater.inflate(R.layout.fragment_categories, container, false);
        toolbar = view.findViewById(R.id.my_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);
        recyclerView = view.findViewById(R.id.recycler_view_categories);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.categories_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_add_category) {
            addCategoryDialogFragment = new AddCategoryDialogFragment();
            addCategoryDialogFragment.setTargetFragment(CategoriesFragment.this, 1);
            addCategoryDialogFragment.show(getParentFragmentManager(), addCategoryDialogFragment.getClass().getName());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFinishEditDialog(String inputText) {
        viewModel.insert(new CategoryEntity(inputText));
        if (onFragmentActionListener != null) {
            onFragmentActionListener.onOpenCategoriesFragment();
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adapter = new CategoriesAdapter(categories, communicator);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        viewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        viewModel.getLiveDataCategories().observe(getViewLifecycleOwner(), new Observer<List<CategoryEntity>>() {
            @Override
            public void onChanged(List<CategoryEntity> categoryEntities) {
                categories = categoryEntities;
                adapter.setCategories(categories);
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        onFragmentActionListener = null;
    }
}
