package com.eugene_poroshin.money_manager.ui.accounts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.repo.viewmodel.AccountsViewModel
import com.eugene_poroshin.money_manager.databinding.ActivityEditAccountBinding
import com.eugene_poroshin.money_manager.repo.database.AccountEntity

class EditAccountActivity : AppCompatActivity(R.layout.activity_edit_account) {

    private var binding: ActivityEditAccountBinding? = null
    //зачем нулабельность
    private var idAccountEntity: Int = 0

    private val viewModelAccount: AccountsViewModel? by lazy { ViewModelProvider(this).get(AccountsViewModel::class.java) }
    //зачем нулабельность

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAccountBinding.bind(findViewById(R.id.activity_edit_account_root))
        initToolbar()

        binding?.buttonSaveAccountNew?.setOnClickListener { saveAccount() }
        val receivedIndent = intent
        if (receivedIndent != null) {
            val accountEntity =
                receivedIndent.getParcelableExtra(ACCOUNT_ENTITY_PARCELABLE_KEY) as AccountEntity?
            idAccountEntity = accountEntity?.id!!
            //!!
            binding?.editTextAccountNameNew?.setText(accountEntity.name)
            binding?.editTextBalanceNew?.setText(accountEntity.balance.toString())
            binding?.editTextCurrencyNew?.setText(accountEntity.currency)
        }
    }

    private fun initToolbar() {
        binding?.toolbarEditAccount?.inflateMenu(R.menu.add_or_edit_account_menu)
        binding?.toolbarEditAccount?.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
        binding?.toolbarEditAccount?.setNavigationOnClickListener { onBackPressed() }
        binding?.toolbarEditAccount?.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_check_account -> saveAccount()
            }
            true
        }
    }

    private fun saveAccount() {
        //этот метод должен быть в viewModel
        val name: String = binding?.editTextAccountNameNew?.text?.toString()?.capitalize().orEmpty()
        val balance: Double = binding?.editTextBalanceNew?.text?.toString()?.toDoubleOrNull() ?: 0.0
        val currency: String =
            when (binding?.editTextCurrencyNew?.text?.toString()) {
                null -> "BYN"
                "" -> "BYN"
                else -> binding?.editTextCurrencyNew?.text.toString().toUpperCase()
            }
        //аналогично AddAccountActivity
        val accountEntity = AccountEntity(name, balance, currency, idAccountEntity)
        viewModelAccount?.update(accountEntity)
        finish()
    }

    companion object {
        const val ACCOUNT_ENTITY_PARCELABLE_KEY = "ACCOUNT_ENTITY_PARCELABLE_KEY"
    }
}