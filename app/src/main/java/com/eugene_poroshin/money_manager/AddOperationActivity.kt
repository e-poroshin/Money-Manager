package com.eugene_poroshin.money_manager

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.eugene_poroshin.money_manager.accounts.AccountsViewModel
import com.eugene_poroshin.money_manager.categories.CategoryViewModel
import com.eugene_poroshin.money_manager.operations.OperationType
import com.eugene_poroshin.money_manager.operations.OperationsViewModel
import com.eugene_poroshin.money_manager.repo.database.AccountEntity
import com.eugene_poroshin.money_manager.repo.database.CategoryEntity
import com.eugene_poroshin.money_manager.repo.database.OperationEntity
import java.util.*

class AddOperationActivity : AppCompatActivity() {

    private var toolbar: Toolbar? = null
    private var radioGroup: RadioGroup? = null
    private var editTextSum: EditText? = null
    private var editTextDescription: EditText? = null
    private var operationEntity: OperationEntity? = null
    private var type: OperationType? = null
    private var spinnerCategories: Spinner? = null
    private var spinnerAccounts: Spinner? = null
    private var adapterCategories: ArrayAdapter<String>? = null
    private var adapterAccounts: ArrayAdapter<String>? = null
    private var categoryNames: List<String> = ArrayList()
    private var accountNames: List<String> = ArrayList()
    private var viewModelCategory: CategoryViewModel? = null
    private var viewModelAccount: AccountsViewModel? = null
    private var viewModelOperation: OperationsViewModel? = null
    private var categories: List<CategoryEntity> = ArrayList()
    private var accounts: List<AccountEntity> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_operation)
        toolbar = findViewById(R.id.toolbar_add_operation)
        setSupportActionBar(toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
            toolbar?.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        }
        radioGroup = findViewById(R.id.radioGroup)
        editTextSum = findViewById(R.id.editTextBalance)
        editTextDescription = findViewById(R.id.editTextDescription)
        findViewById<View>(R.id.buttonSaveOperation).setOnClickListener { saveOperation() }
        type = OperationType.CONSUMPTION
        radioGroup?.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { group, checkedId ->
            if (group.checkedRadioButtonId == R.id.radioButtonConsumption) {
                toolbar?.title = "Расход"
                type = OperationType.CONSUMPTION
            }
            if (group.checkedRadioButtonId == R.id.radioButtonIncome) {
                toolbar?.title = "Доход"
                type = OperationType.INCOME
            }
        })
        spinnerCategories = findViewById(R.id.spinnerCategories)
        spinnerAccounts = findViewById(R.id.spinnerAccounts)
        viewModelCategory = ViewModelProvider(this).get(CategoryViewModel::class.java)
        viewModelCategory!!.liveDataCategoryNames.observe(
            this, Observer { categories ->
                categoryNames = categories
                adapterCategories = ArrayAdapter(
                    baseContext,
                    android.R.layout.simple_spinner_item,
                    categoryNames
                )
                adapterCategories!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerCategories?.adapter = adapterCategories
                spinnerCategories?.setSelection(0)
            })
        viewModelCategory!!.liveDataCategories.observe(
            this, Observer { categoryEntityList -> categories = categoryEntityList })
        viewModelAccount = ViewModelProvider(this).get(AccountsViewModel::class.java)
        viewModelAccount!!.liveDataAccountNames.observe(
            this,
            Observer { accounts ->
                accountNames = accounts
                adapterAccounts = ArrayAdapter(
                    baseContext,
                    android.R.layout.simple_spinner_item,
                    accountNames
                )
                adapterAccounts!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinnerAccounts?.adapter = adapterAccounts
                spinnerAccounts?.setSelection(0)
            })
        viewModelAccount!!.liveDataAccounts.observe(
            this, Observer { accountEntityList -> accounts = accountEntityList })
        viewModelOperation = ViewModelProvider(this).get(OperationsViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.add_operation_menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_check) {
            saveOperation()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun saveOperation() {
        val categoryId = categories[spinnerCategories!!.selectedItemPosition].id
        val accountId = accounts[spinnerAccounts!!.selectedItemPosition].id
        val date = System.currentTimeMillis()
        val sum: Double = if (editTextSum!!.text.toString().isEmpty()) {
            0.0
        } else {
            editTextSum!!.text.toString().toDouble()
        }
        val description: String = if (editTextDescription!!.text.toString().isEmpty()) {
            ""
        } else {
            editTextDescription!!.text.toString()
        }
        operationEntity = OperationEntity(categoryId, accountId, date, sum, description, type)
        viewModelOperation!!.insert(operationEntity)
        val newSumAccount = accounts[spinnerAccounts!!.selectedItemPosition]
        val currentBalance =
            accounts[spinnerAccounts!!.selectedItemPosition].balance
        if (type == OperationType.CONSUMPTION) {
            newSumAccount.balance = currentBalance - sum
        } else if (type == OperationType.INCOME) {
            newSumAccount.balance = currentBalance + sum
        }
        viewModelAccount!!.update(newSumAccount)
        finish()
    }
}