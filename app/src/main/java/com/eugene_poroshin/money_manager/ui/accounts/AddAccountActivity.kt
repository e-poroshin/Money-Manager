package com.eugene_poroshin.money_manager.ui.accounts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.repo.viewmodel.AccountsViewModel
import com.eugene_poroshin.money_manager.databinding.ActivityAddAccountBinding
import com.eugene_poroshin.money_manager.repo.database.AccountEntity

class AddAccountActivity : AppCompatActivity(R.layout.activity_add_account) {

    private var binding: ActivityAddAccountBinding? = null
    private var viewModelAccount: AccountsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAccountBinding.bind(findViewById(R.id.activity_add_account_root))
        initToolbar()
        binding?.buttonSaveAccount?.setOnClickListener { saveAccount() }
        viewModelAccount = ViewModelProvider(this).get(AccountsViewModel::class.java)
    }

    private fun initToolbar() {
        binding?.toolbarAddAccount?.inflateMenu(R.menu.add_or_edit_account_menu)
        binding?.toolbarAddAccount?.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        binding?.toolbarAddAccount?.setNavigationOnClickListener { onBackPressed() }
        binding?.toolbarAddAccount?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_check_account -> saveAccount()
            }
            true
        }
    }

    private fun saveAccount() {
        //это та логика, которая должна быть в viewModel
        val name: String = binding?.editTextAccountName?.text?.toString()?.capitalize().orEmpty()
        val balance: Double = binding?.editTextBalance?.text?.toString()?.toDoubleOrNull() ?: 0.0
        val currency: String =
            when(binding?.editTextCurrency?.text?.toString()) {
                null -> "BYN"
                "" -> "BYN"
                else -> binding?.editTextCurrency?.text.toString().toUpperCase()
            }
        //binding?.editTextCurrency?.text?.toString()?.takeIf { it.isNotBlank() }?.toUpperCase() ?: "BYN"

        val accountEntity = AccountEntity(name, balance, currency)
        viewModelAccount!!.insert(accountEntity)
        //!!
        finish()
    }
}