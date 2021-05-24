package com.eugene_poroshin.money_manager.ui.accounts

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.databinding.FragmentAccountsBinding
import com.eugene_poroshin.money_manager.di.App
import com.eugene_poroshin.money_manager.repo.database.AccountEntity
import javax.inject.Inject

class AccountsFragment : Fragment(R.layout.fragment_accounts) {

    private var binding: FragmentAccountsBinding? = null

    @Inject
    lateinit var viewModel: AccountsViewModel

    private lateinit var accountsAdapter: AccountsAdapter

    private val communicator = object : AccountsAdapter.OnAccountItemClick {
        override fun onItemClick(accountEntity: AccountEntity) {
            val intent = Intent(requireActivity(), EditAccountActivity::class.java)
            intent.putExtra(ACCOUNT_ENTITY_PARCELABLE_KEY, accountEntity as Parcelable)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.fragmentSubComponentBuilder().with(this).build().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAccountsBinding.bind(view)
        initToolbar()
        accountsAdapter = AccountsAdapter(communicator)
        binding?.recyclerViewAccounts?.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = accountsAdapter
        }
        viewModel.liveDataAccounts.observe(viewLifecycleOwner, { accountEntities ->
            accountsAdapter.accounts = accountEntities
            binding?.myToolbar?.title = "Баланс: " + getBalance(accountEntities) + " BYN"
        })
    }

    private fun initToolbar() {
        binding?.myToolbar?.apply {
            inflateMenu(R.menu.accounts_menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_add -> startActivity(
                        Intent(
                            requireActivity(),
                            AddAccountActivity::class.java
                        )
                    )
                }
                true
            }
        }
    }

    private fun getBalance(accounts: List<AccountEntity>): Double {
        return accounts.sumOf { it.balance }
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {
        const val ACCOUNT_ENTITY_PARCELABLE_KEY = "ACCOUNT_ENTITY_PARCELABLE_KEY"
        fun getInstance(): AccountsFragment = AccountsFragment()
    }
}