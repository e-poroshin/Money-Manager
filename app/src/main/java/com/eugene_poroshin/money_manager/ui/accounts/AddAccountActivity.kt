package com.eugene_poroshin.money_manager.ui.accounts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.databinding.ActivityAddAccountBinding
import org.koin.android.viewmodel.ext.android.viewModel

class AddAccountActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddAccountBinding
    private val accountsViewModel: AccountsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_add_account)
        binding.lifecycleOwner = this
        binding.viewModel = accountsViewModel

        accountsViewModel.finishEvent.observe(this) {
            finish()
        }

        initToolbar()
    }

    private fun initToolbar() {
        binding.toolbarAddAccount.apply {
            inflateMenu(R.menu.add_or_edit_account_menu)
            setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
            setNavigationOnClickListener { onBackPressed() }
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_check_account -> {
                        accountsViewModel.addAccount()
                    }
                }
                true
            }
        }
    }
}