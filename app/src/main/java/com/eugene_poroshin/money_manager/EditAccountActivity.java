package com.eugene_poroshin.money_manager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.eugene_poroshin.money_manager.accounts.AccountsViewModel;
import com.eugene_poroshin.money_manager.repo.database.AccountEntity;

import java.util.ArrayList;
import java.util.List;


public class EditAccountActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText editTextAccountName;
    private EditText editTextBalance;
    private EditText editTextCurrency;
    private AccountEntity accountEntity;
    private AccountsViewModel viewModelAccount;
    private List<AccountEntity> accounts = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_account);

        toolbar = findViewById(R.id.toolbar_edit_account);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        }
        editTextAccountName = findViewById(R.id.editTextAccountNameNew);
        editTextBalance = findViewById(R.id.editTextBalanceNew);
        editTextCurrency = findViewById(R.id.editTextCurrencyNew);

        findViewById(R.id.buttonSaveAccountNew).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editAccount();
            }
        });

        viewModelAccount = new ViewModelProvider(this).get(AccountsViewModel.class);
        viewModelAccount.getLiveDataAccounts().observe(this, new Observer<List<AccountEntity>>() {
            @Override
            public void onChanged(List<AccountEntity> accountEntityList) {
                accounts = accountEntityList;
            }
        });

        Intent receivedIndent = getIntent();
        if (receivedIndent != null) {
            accountEntity = (AccountEntity) receivedIndent.getSerializableExtra(AccountEntity.class.getSimpleName());

            editTextAccountName.setText(accountEntity.getName());
            editTextBalance.setText(String.valueOf(accountEntity.getBalance()));
            editTextCurrency.setText(accountEntity.getCurrency());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_account_menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_check_edit_account) {
            editAccount();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void editAccount() {
        String name;
        if (editTextAccountName.getText().toString().isEmpty()) {
            name = "";
        } else {
            name = editTextAccountName.getText().toString();
        }
        double balance;
        if (editTextBalance.getText().toString().isEmpty()) {
            balance = 0.0;
        } else {
            balance = Double.parseDouble(editTextBalance.getText().toString());
        }
        String currency;
        if (editTextCurrency.getText().toString().isEmpty()) {
            currency = "";
        } else {
            currency = editTextCurrency.getText().toString();
        }
        accountEntity.setName(name);
        accountEntity.setBalance(balance);
        accountEntity.setCurrency(currency);

        viewModelAccount.update(accountEntity);
        finish();
    }

}
