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
import com.eugene_poroshin.money_manager.ui.FragmentCommunicator
import com.eugene_poroshin.money_manager.repo.database.AccountEntity
import com.eugene_poroshin.money_manager.repo.viewmodel.AccountsViewModel
import java.util.*
import javax.inject.Inject

class AccountsFragment : Fragment(R.layout.fragment_accounts) {

    private var binding: FragmentAccountsBinding? = null

    @Inject
    lateinit var viewModel: AccountsViewModel

    private var adapter: AccountsAdapter? = null
    private var accounts: List<AccountEntity> = ArrayList()
    private val communicator = object : FragmentCommunicator {
        override fun onItemClickListener(categoryName: String?) {}
        override fun onItemAccountClickListener(accountEntity: AccountEntity?) {
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
        adapter = AccountsAdapter(accounts, communicator)
        binding!!.recyclerViewAccounts.layoutManager = LinearLayoutManager(activity)
        binding!!.recyclerViewAccounts.adapter = adapter
        viewModel.liveDataAccounts.observe(viewLifecycleOwner, { accountEntities ->
            accounts = accountEntities
            adapter!!.setAccounts(accounts)
            binding!!.myToolbar.title = "Баланс: " + getBalance(accounts) + " BYN"
        })
    }

    private fun initToolbar() {
        binding!!.myToolbar.inflateMenu(R.menu.accounts_menu)
        binding!!.myToolbar.setOnMenuItemClickListener {
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

    private fun getBalance(accounts: List<AccountEntity>): Double {
        var sum = 0.0
        accounts.forEach { accountEntity ->
            sum += accountEntity.balance
        }
        return sum
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    companion object {

        const val ACCOUNT_ENTITY_PARCELABLE_KEY = "ACCOUNT_ENTITY_PARCELABLE_KEY"
        private var INSTANCE: AccountsFragment? = null

        fun getInstance(): AccountsFragment {
            return if (INSTANCE == null) {
                INSTANCE = AccountsFragment()
                INSTANCE!!
            } else INSTANCE!!
        }
    }
}