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

class EditAccountActivity : AppCompatActivity() {

    private var toolbar: Toolbar? = null
    private var editTextAccountName: EditText? = null
    private var editTextBalance: EditText? = null
    private var editTextCurrency: EditText? = null
    private var accountEntity: AccountEntity? = null
    private var viewModelAccount: AccountsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_account)
        toolbar = findViewById(R.id.toolbar_edit_account)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
            toolbar?.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        }
        editTextAccountName = findViewById(R.id.editTextAccountNameNew)
        editTextBalance = findViewById(R.id.editTextBalanceNew)
        editTextCurrency = findViewById(R.id.editTextCurrencyNew)
        findViewById<View>(R.id.buttonSaveAccountNew).setOnClickListener { editAccount() }
        viewModelAccount =
            ViewModelProvider(this).get(AccountsViewModel::class.java)
        val receivedIndent = intent
        if (receivedIndent != null) {
            accountEntity =
                receivedIndent.getSerializableExtra(AccountEntity::class.java.simpleName) as AccountEntity
            editTextAccountName?.setText(accountEntity!!.name)
            editTextBalance?.setText(accountEntity!!.balance.toString())
            editTextCurrency?.setText(accountEntity!!.currency)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.edit_account_menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_check_edit_account) {
            editAccount()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun editAccount() {
        val name: String = if (editTextAccountName?.text.toString().isEmpty()) {
            ""
        } else {
            editTextAccountName?.text.toString()
        }

        val balance: Double = if (editTextBalance?.text.toString().isEmpty()) {
            0.0
        } else {
            editTextBalance?.text.toString().toDouble()
        }

        val currency: String = if (editTextCurrency?.text.toString().isEmpty()) {
            ""
        } else {
            editTextCurrency?.text.toString()
        }
        accountEntity?.name = name
        accountEntity?.balance = balance
        accountEntity?.currency = currency
        viewModelAccount?.update(accountEntity)
        finish()
    }
}