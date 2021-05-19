package com.eugene_poroshin.money_manager.ui.accounts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.databinding.ActivityEditAccountBinding
import com.eugene_poroshin.money_manager.repo.database.AccountEntity

class EditAccountActivity : AppCompatActivity(R.layout.activity_edit_account) {

    private lateinit var binding: ActivityEditAccountBinding
    private var idAccountEntity: Int = 0

    private val viewModelAccount: AccountsViewModel by lazy { ViewModelProvider(this).get(
        AccountsViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAccountBinding.bind(findViewById(R.id.activity_edit_account_root))
        initToolbar()

        binding.buttonSaveAccountNew.setOnClickListener { saveAccount() }
        intent?.let {
            val accountEntity = it.getParcelableExtra(ACCOUNT_ENTITY_PARCELABLE_KEY) as AccountEntity?
            idAccountEntity = accountEntity?.id ?: 0
            binding.editTextAccountNameNew.setText(accountEntity?.name)
            binding.editTextBalanceNew.setText(accountEntity?.balance.toString())
            binding.editTextCurrencyNew.setText(accountEntity?.currency)
        }
    }

    private fun initToolbar() {
        binding.toolbarEditAccount.apply {
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
        //этот метод должен быть в viewModel
        val name: String = binding.editTextAccountNameNew.text?.toString()?.capitalize().orEmpty()
        val balance: Double = binding.editTextBalanceNew.text?.toString()?.toDoubleOrNull() ?: 0.0
        val currency: String = binding.editTextCurrencyNew.text?.toString()?.takeIf { it.isNotBlank() }?.toUpperCase() ?: "BYN"
        val accountEntity = AccountEntity(name, balance, currency, idAccountEntity)
        viewModelAccount.update(accountEntity)
        finish()
    }

    companion object {
        const val ACCOUNT_ENTITY_PARCELABLE_KEY = "ACCOUNT_ENTITY_PARCELABLE_KEY"
    }
}