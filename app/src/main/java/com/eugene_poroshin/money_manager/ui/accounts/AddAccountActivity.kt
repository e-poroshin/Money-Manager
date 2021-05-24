package com.eugene_poroshin.money_manager.ui.accounts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.databinding.ActivityAddAccountBinding
import com.eugene_poroshin.money_manager.repo.database.AccountEntity
import java.util.*

class AddAccountActivity : AppCompatActivity(R.layout.activity_add_account) {

    private lateinit var binding: ActivityAddAccountBinding
    private val viewModelAccount: AccountsViewModel by lazy {
        ViewModelProvider(this).get(AccountsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAccountBinding.bind(findViewById(R.id.activity_add_account_root))
        initToolbar()
        binding.buttonSaveAccount.setOnClickListener { saveAccount() }
    }

    private fun initToolbar() {
        binding.toolbarAddAccount.apply {
            inflateMenu(R.menu.add_or_edit_account_menu)
            setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
            setNavigationOnClickListener { onBackPressed() }
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_check_account -> saveAccount()
                }
                true
            }
        }
    }

    private fun saveAccount() {
        //это та логика, которая должна быть в viewModel
        val name: String = binding.editTextAccountName.text?.toString()?.replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString()
        }.orEmpty()
        val balance: Double = binding.editTextBalance.text?.toString()?.toDoubleOrNull() ?: 0.0
        val currency: String = binding.editTextCurrency.text?.toString()
            ?.takeIf { it.isNotBlank() }?.uppercase(Locale.getDefault()) ?: "BYN"

        val accountEntity = AccountEntity(name, balance, currency)
        viewModelAccount.insert(accountEntity)
        finish()
    }
}