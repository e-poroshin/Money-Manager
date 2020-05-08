package com.eugene_poroshin.money_manager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.eugene_poroshin.money_manager.accounts.AccountsViewModel;
import com.eugene_poroshin.money_manager.categories.CategoryViewModel;
import com.eugene_poroshin.money_manager.operations.OperationType;
import com.eugene_poroshin.money_manager.operations.OperationsViewModel;
import com.eugene_poroshin.money_manager.repo.database.AccountEntity;
import com.eugene_poroshin.money_manager.repo.database.CategoryEntity;
import com.eugene_poroshin.money_manager.repo.database.OperationEntity;

import java.util.ArrayList;
import java.util.List;


public class AddOperationActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private RadioGroup radioGroup;
    private EditText editTextSum;
    private EditText editTextDescription;
    private OperationEntity operationEntity;
    private OperationType type;
    private Spinner spinnerCategories;
    private Spinner spinnerAccounts;
    private ArrayAdapter<String> adapterCategories;
    private ArrayAdapter<String> adapterAccounts;
    private List<String> categoryNames = new ArrayList<>();
    private List<String> accountNames = new ArrayList<>();
    private CategoryViewModel viewModelCategory;
    private AccountsViewModel viewModelAccount;
    private OperationsViewModel viewModelOperation;
    private List<CategoryEntity> categories = new ArrayList<>();
    private List<AccountEntity> accounts = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_operation);

        toolbar = findViewById(R.id.toolbar_add_operation);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        }
        radioGroup = findViewById(R.id.radioGroup);
        editTextSum = findViewById(R.id.editTextBalance);
        editTextDescription = findViewById(R.id.editTextDescription);

        findViewById(R.id.buttonSaveOperation).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOperation();
            }
        });

        type = OperationType.CONSUMPTION;
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (group.getCheckedRadioButtonId() == R.id.radioButtonConsumption) {
                    toolbar.setTitle("Расход");
                    type = OperationType.CONSUMPTION;
                }
                if (group.getCheckedRadioButtonId() == R.id.radioButtonIncome) {
                    toolbar.setTitle("Доход");
                    type = OperationType.INCOME;
                }
            }
        });
        spinnerCategories = findViewById(R.id.spinnerCategories);
        spinnerAccounts = findViewById(R.id.spinnerAccounts);

        viewModelCategory = new ViewModelProvider(this).get(CategoryViewModel.class);
        viewModelCategory.getLiveDataCategoryNames().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> categories) {
                categoryNames = categories;
                adapterCategories = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, categoryNames);
                adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCategories.setAdapter(adapterCategories);
                spinnerCategories.setSelection(0);
            }
        });
        viewModelCategory.getLiveDataCategories().observe(this, new Observer<List<CategoryEntity>>() {
            @Override
            public void onChanged(List<CategoryEntity> categoryEntityList) {
                categories = categoryEntityList;
            }
        });

        viewModelAccount = new ViewModelProvider(this).get(AccountsViewModel.class);
        viewModelAccount.getLiveDataAccountNames().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> accounts) {
                accountNames = accounts;
                adapterAccounts = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_item, accountNames);
                adapterAccounts.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerAccounts.setAdapter(adapterAccounts);
                spinnerAccounts.setSelection(0);
            }
        });
        viewModelAccount.getLiveDataAccounts().observe(this, new Observer<List<AccountEntity>>() {
            @Override
            public void onChanged(List<AccountEntity> accountEntityList) {
                accounts = accountEntityList;
            }
        });

        viewModelOperation = new ViewModelProvider(this).get(OperationsViewModel.class);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_operation_menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_check) {
            saveOperation();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveOperation() {
        int categoryId = categories.get(spinnerCategories.getSelectedItemPosition()).getId();
        int accountId = accounts.get(spinnerAccounts.getSelectedItemPosition()).getId();
        long date = System.currentTimeMillis();
        double sum;
        if (editTextSum.getText().toString().isEmpty()) {
            sum = 0.0;
        } else {
            sum = Double.parseDouble(editTextSum.getText().toString());
        }
        String description;
        if (editTextDescription.getText().toString().isEmpty()) {
            description = "";
        } else {
            description = editTextDescription.getText().toString();
        }

        operationEntity = new OperationEntity(categoryId, accountId, date, sum, description, type);
        viewModelOperation.insert(operationEntity);

        AccountEntity newSumAccount = accounts.get(spinnerAccounts.getSelectedItemPosition());
        double currentBalance = accounts.get(spinnerAccounts.getSelectedItemPosition()).getBalance();
        if (type.equals(OperationType.CONSUMPTION)) {
            newSumAccount.setBalance(currentBalance - sum);
        } else if (type.equals(OperationType.INCOME)) {
            newSumAccount.setBalance(currentBalance + sum);
        }
        viewModelAccount.update(newSumAccount);

        finish();
    }

}
