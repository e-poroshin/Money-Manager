package com.eugene_poroshin.money_manager.ui.accounts

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.databinding.FragmentAccountsBinding
import com.eugene_poroshin.money_manager.repo.database.AccountEntity
import org.koin.android.viewmodel.ext.android.getViewModel


class AccountsFragment : Fragment() {

    private var binding: FragmentAccountsBinding? = null

    private val accountsViewModel = getViewModel<AccountsViewModel>()

    private lateinit var accountsAdapter: AccountsAdapter

    private val onItemClick = object : AccountsAdapter.OnAccountItemClick {
        override fun onItemClick(accountEntity: AccountEntity) {
            val intent = Intent(requireActivity(), EditAccountActivity::class.java)
            intent.putExtra(ACCOUNT_ENTITY_PARCELABLE_KEY, accountEntity)
            startActivity(intent)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_accounts, container, false)
        binding?.lifecycleOwner = this
        binding?.viewModel = accountsViewModel
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initToolbar()
        accountsAdapter = AccountsAdapter(onItemClick)
        binding?.recyclerViewAccounts?.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = accountsAdapter
        }
        accountsViewModel.liveDataAccounts.observe(viewLifecycleOwner, { accountEntities ->
            accountsAdapter.accounts = accountEntities
            binding?.myToolbar?.title = "Баланс: " + getBalance(accountEntities) + " BYN"
        })
    }

    private fun initToolbar() {
        // setSupportActionBar??
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