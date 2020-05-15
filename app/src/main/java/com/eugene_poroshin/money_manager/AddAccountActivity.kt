package com.eugene_poroshin.money_manager

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.eugene_poroshin.money_manager.accounts.AccountsViewModel
import com.eugene_poroshin.money_manager.repo.database.AccountEntity

class AddAccountActivity : AppCompatActivity() {

    private var toolbar: Toolbar? = null
    private var editTextAccountName: EditText? = null
    private var editTextBalance: EditText? = null
    private var editTextCurrency: EditText? = null
    private var accountEntity: AccountEntity? = null
    private var viewModelAccount: AccountsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_account)
        toolbar = findViewById(R.id.toolbar_add_account)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
            toolbar?.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        }
        editTextAccountName = findViewById(R.id.editTextAccountName)
        editTextBalance = findViewById(R.id.editTextBalance)
        editTextCurrency = findViewById(R.id.editTextCurrency)
        findViewById<View>(R.id.buttonSaveAccount).setOnClickListener { saveAccount() }
        viewModelAccount = ViewModelProvider(this).get(AccountsViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.add_account_menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_check_account) {
            saveAccount()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveAccount() {
        val name: String = if (editTextAccountName!!.text.toString().isEmpty()) {
            ""
        } else {
            editTextAccountName!!.text.toString()
        }
        val balance: Double = if (editTextBalance!!.text.toString().isEmpty()) {
            0.0
        } else {
            editTextBalance!!.text.toString().toDouble()
        }
        val currency: String = if (editTextCurrency!!.text.toString().isEmpty()) {
            ""
        } else {
            editTextCurrency!!.text.toString()
        }
        accountEntity = AccountEntity(name, balance, currency)
        viewModelAccount!!.insert(accountEntity)
        finish()
    }
}