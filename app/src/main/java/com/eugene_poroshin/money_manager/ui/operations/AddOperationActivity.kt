package com.eugene_poroshin.money_manager.ui.operations

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.databinding.ActivityAddOperationBinding
import com.eugene_poroshin.money_manager.repo.database.AccountEntity
import com.eugene_poroshin.money_manager.repo.database.CategoryEntity
import com.eugene_poroshin.money_manager.ui.accounts.AccountsViewModel
import com.eugene_poroshin.money_manager.ui.categories.CategoryViewModel

class AddOperationActivity : AppCompatActivity(R.layout.activity_add_operation) {

    private var binding: ActivityAddOperationBinding? = null

    private var type: OperationType = OperationType.EXPENSE

    private val categoryViewModel
            by lazy { ViewModelProvider(this).get(CategoryViewModel::class.java) }
    private val accountsViewModel
            by lazy { ViewModelProvider(this).get(AccountsViewModel::class.java) }
    private val operationsViewModel
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
        initRadioButtons()
        binding?.buttonSaveOperation?.setOnClickListener { operationsViewModel.saveOperation() }

        operationsViewModel.finishEvent.observe(this) {
            finish()
        }

        categoryViewModel.liveDataCategories.observe(
            this, { categoryEntityList -> categories = categoryEntityList }
        )
        accountsViewModel.liveDataAccounts.observe(
            this, { accountEntityList -> accounts = accountEntityList }
        )
    }

    private fun initToolbar() {
        binding?.toolbarAddOperation?.inflateMenu(R.menu.add_operation_menu)
        binding?.toolbarAddOperation?.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        binding?.toolbarAddOperation?.setNavigationOnClickListener { onBackPressed() }
        binding?.toolbarAddOperation?.setOnMenuItemClickListener {
            if (it.itemId == R.id.action_check) {
                operationsViewModel.saveOperation()
            }
            true
        }
    }

    private fun initRadioButtons() {
        //todo destructing?
        binding?.radioGroup?.setOnCheckedChangeListener { group, _ ->
            val (newTitle: String, newType: OperationType) = when (group.checkedRadioButtonId) {
                R.id.radioButtonConsumption -> ("Расход" to OperationType.EXPENSE)
                R.id.radioButtonIncome -> ("Доход" to OperationType.INCOME)
                else -> throw IllegalArgumentException()
            }
            binding?.toolbarAddOperation?.title = newTitle
            type = newType
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
}