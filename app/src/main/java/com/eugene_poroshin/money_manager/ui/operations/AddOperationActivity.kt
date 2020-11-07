package com.eugene_poroshin.money_manager.ui.operations

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.databinding.ActivityAddOperationBinding
import com.eugene_poroshin.money_manager.repo.database.AccountEntity
import com.eugene_poroshin.money_manager.repo.database.CategoryEntity
import com.eugene_poroshin.money_manager.repo.database.OperationEntity
import com.eugene_poroshin.money_manager.repo.viewmodel.AccountsViewModel
import com.eugene_poroshin.money_manager.repo.viewmodel.CategoryViewModel
import com.eugene_poroshin.money_manager.repo.viewmodel.OperationsViewModel

class AddOperationActivity : AppCompatActivity(R.layout.activity_add_operation) {

    //todo binding null init in other activities
    private var binding: ActivityAddOperationBinding? = null

    private var type: OperationType = OperationType.EXPENSE

    //todo adapter, accountNames check List, set...?
    private val viewModelCategory
            by lazy { ViewModelProvider(this).get(CategoryViewModel::class.java) }
    private val viewModelAccount
            by lazy { ViewModelProvider(this).get(AccountsViewModel::class.java) }
    private val viewModelOperation
            by lazy { ViewModelProvider(this).get(OperationsViewModel::class.java) }

    private var categories: List<CategoryEntity> = emptyList()
        set(value) {
            field = value
            updateCategoriesSpinner(value)
        }
    private var accounts: List<AccountEntity> = emptyList()
        set(value) {
            field = value
            updateAccountsSpinner(value)
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddOperationBinding.bind(findViewById(R.id.activity_add_operation_root))
        initToolbar()
        binding?.buttonSaveOperation?.setOnClickListener { saveOperation() }
        binding?.radioGroup?.setOnCheckedChangeListener { group, _ ->
            val (newTitle: String, newType: OperationType) = when (group.checkedRadioButtonId) {
                R.id.radioButtonConsumption -> ("Расход" to OperationType.EXPENSE)
                R.id.radioButtonIncome -> ("Доход" to OperationType.INCOME)
                else -> throw IllegalArgumentException()
            }
            binding?.toolbarAddOperation?.title = newTitle
            type = newType
        }
        //todo destructing?

        viewModelCategory.liveDataCategories.observe(
            this, { categoryEntityList ->
                categories = categoryEntityList
            })
        viewModelAccount.liveDataAccounts.observe(
            this, { accountEntityList -> accounts = accountEntityList })
    }

    private fun initToolbar() {
        binding?.toolbarAddOperation?.inflateMenu(R.menu.add_operation_menu)
        binding?.toolbarAddOperation?.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        binding?.toolbarAddOperation?.setNavigationOnClickListener { onBackPressed() }
        binding?.toolbarAddOperation?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_check -> saveOperation()
            }
            true
        }
    }

    private fun updateCategoriesSpinner(value: List<CategoryEntity>) {
        val adapterCategories = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            value.map { categoryList -> categoryList.name }
        )
        adapterCategories.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding?.spinnerCategories?.adapter = adapterCategories
        binding?.spinnerCategories?.setSelection(0)
    }

    private fun updateAccountsSpinner(value: List<AccountEntity>) {
        val adapterAccounts = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            value.map { accountList -> accountList.name }
        )
        adapterAccounts.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding?.spinnerAccounts?.adapter = adapterAccounts
        binding?.spinnerAccounts?.setSelection(0)
    }

    private fun saveOperation() {
        val categoryId = categories[binding!!.spinnerCategories.selectedItemPosition].id
        val accountId = accounts[binding!!.spinnerAccounts.selectedItemPosition].id
        val date = System.currentTimeMillis()
        val sum: Double = binding!!.editTextBalance.text.toString().toDoubleOrNull() ?: 0.0
        val description: String = binding!!.editTextDescription.text?.toString().orEmpty()
        val operationEntity = OperationEntity(categoryId, accountId, date, sum, description, type)
        viewModelOperation.insert(operationEntity)
        val newSumAccount = accounts[binding!!.spinnerAccounts.selectedItemPosition]
        val currentBalance = accounts[binding!!.spinnerAccounts.selectedItemPosition].balance
        when (type) {
            OperationType.EXPENSE -> newSumAccount.balance = currentBalance - sum
            OperationType.INCOME -> newSumAccount.balance = currentBalance + sum
        }
        viewModelAccount.update(newSumAccount)
        finish()
    }
}