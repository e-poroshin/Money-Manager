package com.eugene_poroshin.money_manager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
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


public class AddAccountActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private EditText editTextAccountName;
    private EditText editTextBalance;
    private EditText editTextCurrency;
    private AccountEntity accountEntity;
    private AccountsViewModel viewModelAccount;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_account);

        toolbar = findViewById(R.id.toolbar_add_account);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            toolbar.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp);
        }
        editTextAccountName = findViewById(R.id.editTextAccountName);
        editTextBalance = findViewById(R.id.editTextBalance);
        editTextCurrency = findViewById(R.id.editTextCurrency);

        findViewById(R.id.buttonSaveAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveAccount();
            }
        });

        viewModelAccount = new ViewModelProvider(this).get(AccountsViewModel.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_account_menu, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_check_account) {
            saveAccount();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void saveAccount() {
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

        accountEntity = new AccountEntity(name, balance, currency);
        viewModelAccount.insert(accountEntity);

        finish();
    }

}
