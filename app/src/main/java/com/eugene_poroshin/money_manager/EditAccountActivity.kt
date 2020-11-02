package com.eugene_poroshin.money_manager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.eugene_poroshin.money_manager.accounts.AccountsViewModel
import com.eugene_poroshin.money_manager.databinding.ActivityEditAccountBinding
import com.eugene_poroshin.money_manager.repo.database.AccountEntity

class EditAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditAccountBinding
    private var idAccountEntity: Int = 0

    //todo how to init AccountsViewModel?
    private var viewModelAccount: AccountsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAccountBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initToolbar()

        binding.buttonSaveAccountNew.setOnClickListener { saveAccount() }
        viewModelAccount = ViewModelProvider(this).get(AccountsViewModel::class.java)
        val receivedIndent = intent
        if (receivedIndent != null) {
            val accountEntity =
                receivedIndent.getParcelableExtra(ACCOUNT_ENTITY_PARCELABLE_KEY) as AccountEntity?
            idAccountEntity = accountEntity?.id!!
            binding.editTextAccountNameNew.setText(accountEntity.name)
            binding.editTextBalanceNew.setText(accountEntity.balance.toString())
            binding.editTextCurrencyNew.setText(accountEntity.currency)
        }
    }

    private fun initToolbar() {
        binding.toolbarEditAccount.inflateMenu(R.menu.add_or_edit_account_menu)
        binding.toolbarEditAccount.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        binding.toolbarEditAccount.setNavigationOnClickListener { onBackPressed() }
        binding.toolbarEditAccount.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_check_account -> saveAccount()
            }
            true
        }
    }

    private fun saveAccount() {
        val name: String = binding.editTextAccountNameNew.text?.toString()?.capitalize().orEmpty()
        val balance: Double = binding.editTextBalanceNew.text?.toString()?.toDoubleOrNull() ?: 0.0
        val currency: String =
            when (binding.editTextCurrencyNew.text?.toString()) {
                null -> "BYN"
                "" -> "BYN"
                else -> binding.editTextCurrencyNew.text.toString().toUpperCase()
            }
        val accountEntity = AccountEntity(name, balance, currency, idAccountEntity)
        viewModelAccount?.update(accountEntity)
        finish()
    }

    companion object {
        const val ACCOUNT_ENTITY_PARCELABLE_KEY = "ACCOUNT_ENTITY_PARCELABLE_KEY"
    }
}