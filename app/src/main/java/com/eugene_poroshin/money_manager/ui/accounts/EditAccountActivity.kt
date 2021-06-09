package com.eugene_poroshin.money_manager.ui.accounts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.databinding.ActivityEditAccountBinding
import com.eugene_poroshin.money_manager.repo.database.AccountEntity

class EditAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditAccountBinding
    private var idAccountEntity: Int = 0
    private val accountsViewModel: AccountsViewModel by lazy {
        ViewModelProvider(this).get(AccountsViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_edit_account)
        binding.lifecycleOwner = this
        binding.viewModel = accountsViewModel

        accountsViewModel.finishEvent.observe(this) {
            finish()
        }

        initToolbar()

        intent?.let {
            val accountEntity = it.getParcelableExtra(ACCOUNT_ENTITY_PARCELABLE_KEY) as AccountEntity?
            idAccountEntity = accountEntity?.id ?: 0
            binding.editTextAccountNameNew.setText(accountEntity?.name)
            binding.editTextBalanceNew.setText(accountEntity?.balance.toString())
            binding.editTextCurrencyNew.setText(accountEntity?.currency)
        }
        binding.idAccountEntity = idAccountEntity
    }

    private fun initToolbar() {
        binding.toolbarEditAccount.apply {
            inflateMenu(R.menu.add_or_edit_account_menu)
            setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
            setNavigationOnClickListener { onBackPressed() }
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_check_account -> {
                        accountsViewModel.updateAccount(idAccountEntity)
                    }
                }
                true
            }
        }
    }

    companion object {
        const val ACCOUNT_ENTITY_PARCELABLE_KEY = "ACCOUNT_ENTITY_PARCELABLE_KEY"
    }
}