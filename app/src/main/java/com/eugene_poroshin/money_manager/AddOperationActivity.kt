package com.eugene_poroshin.money_manager

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.eugene_poroshin.money_manager.accounts.AccountsViewModel
import com.eugene_poroshin.money_manager.categories.CategoryViewModel
import com.eugene_poroshin.money_manager.databinding.ActivityAddOperationBinding
import com.eugene_poroshin.money_manager.operations.OperationType
import com.eugene_poroshin.money_manager.operations.OperationsViewModel
import com.eugene_poroshin.money_manager.repo.database.AccountEntity
import com.eugene_poroshin.money_manager.repo.database.CategoryEntity
import com.eugene_poroshin.money_manager.repo.database.OperationEntity
import java.util.*

class AddOperationActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddOperationBinding

    private lateinit var operationEntity: OperationEntity
    private var type: OperationType = OperationType.EXPENSE
    private var adapterCategories: ArrayAdapter<String>? = null
    private var adapterAccounts: ArrayAdapter<String>? = null
    private var categoryNames: List<String> = ArrayList()
    private var accountNames: List<String> = ArrayList()

    //todo - is it OK by lazy? \/
    private val viewModelCategory
            by lazy { ViewModelProvider(this).get(CategoryViewModel::class.java) }
    private val viewModelAccount
            by lazy { ViewModelProvider(this).get(AccountsViewModel::class.java) }
    private var viewModelOperation: OperationsViewModel? = null
    private var categories: List<CategoryEntity> = ArrayList()
    private var accounts: List<AccountEntity> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddOperationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        binding.buttonSaveOperation.setOnClickListener { saveOperation() }
        binding.radioGroup.setOnCheckedChangeListener { group, _ ->
            when (group.checkedRadioButtonId) {
                R.id.radioButtonConsumption -> {
                    binding.toolbarAddOperation.title = "Расход"
                    type = OperationType.EXPENSE
                }
                R.id.radioButtonIncome -> {
                    binding.toolbarAddOperation.title = "Доход"
                    type = OperationType.INCOME
                }
            }
        }
        viewModelCategory.liveDataCategoryNames.observe(
            this, { categories ->
                categoryNames = categories
                adapterCategories = ArrayAdapter(
                    baseContext,
                    android.R.layout.simple_spinner_item,
                    categoryNames
                )
                adapterCategories!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerCategories.adapter = adapterCategories
                binding.spinnerCategories.setSelection(0)
            })
        viewModelCategory.liveDataCategories.observe(
            this, { categoryEntityList -> categories = categoryEntityList })
        viewModelAccount.liveDataAccountNames.observe(
            this,
            { accounts ->
                accountNames = accounts
                adapterAccounts = ArrayAdapter(
                    baseContext,
                    android.R.layout.simple_spinner_item,
                    accountNames
                )
                adapterAccounts!!.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                binding.spinnerAccounts.adapter = adapterAccounts
                binding.spinnerAccounts.setSelection(0)
            })
        viewModelAccount.liveDataAccounts.observe(
            this, { accountEntityList -> accounts = accountEntityList })
        viewModelOperation = ViewModelProvider(this).get(OperationsViewModel::class.java)
    }
    //todo liveDataCategories and liveDataCategoryNames, -//- AccountNames and accounts?

    private fun initToolbar() {
        binding.toolbarAddOperation.inflateMenu(R.menu.add_operation_menu)
        binding.toolbarAddOperation.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        binding.toolbarAddOperation.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarAddOperation.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_check -> saveOperation()
            }
            true
        }
    }

    private fun saveOperation() {
        val categoryId = categories[binding.spinnerCategories.selectedItemPosition].id
        val accountId = accounts[binding.spinnerAccounts.selectedItemPosition].id
        val date = System.currentTimeMillis()
        val sum: Double = binding.editTextBalance.text.toString().toDoubleOrNull() ?: 0.0
        val description: String = binding.editTextDescription.text?.toString().orEmpty()
        operationEntity = OperationEntity(categoryId, accountId, date, sum, description, type)
        viewModelOperation!!.insert(operationEntity)
        val newSumAccount = accounts[binding.spinnerAccounts.selectedItemPosition]
        val currentBalance = accounts[binding.spinnerAccounts.selectedItemPosition].balance
        when (type) {
            OperationType.EXPENSE -> newSumAccount.balance = currentBalance - sum
            OperationType.INCOME -> newSumAccount.balance = currentBalance + sum
        }
        viewModelAccount.update(newSumAccount)
        finish()
    }
}