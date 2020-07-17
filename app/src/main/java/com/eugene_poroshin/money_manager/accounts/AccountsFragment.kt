package com.eugene_poroshin.money_manager.accounts

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.eugene_poroshin.money_manager.AddAccountActivity
import com.eugene_poroshin.money_manager.EditAccountActivity
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.di.App
import com.eugene_poroshin.money_manager.fragments.FragmentCommunicator
import com.eugene_poroshin.money_manager.repo.database.AccountEntity
import java.io.Serializable
import java.util.*
import javax.inject.Inject

class AccountsFragment : Fragment() {

    @Inject
    lateinit var viewModel: AccountsViewModel

    private var toolbar: Toolbar? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: AccountsAdapter? = null
    private var accounts: List<AccountEntity> = ArrayList()
    private val communicator = object : FragmentCommunicator {
        override fun onItemClickListener(categoryName: String?) {}
        override fun onItemAccountClickListener(accountEntity: AccountEntity?) {
            val intent = Intent(requireActivity(), EditAccountActivity::class.java)
            intent.putExtra(AccountEntity::class.java.simpleName, accountEntity as Serializable)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        App.appComponent.fragmentSubComponentBuilder().with(this).build().inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_accounts, container, false)
        toolbar = view.findViewById(R.id.my_toolbar)
        (activity as AppCompatActivity?)!!.setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        recyclerView = view.findViewById(R.id.recycler_view_accounts)
        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.accounts_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_add) {
            val intent = Intent(requireActivity(), AddAccountActivity::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = AccountsAdapter(accounts, communicator)
        recyclerView!!.layoutManager = LinearLayoutManager(activity)
        recyclerView!!.adapter = adapter
        viewModel.liveDataAccounts.observe(viewLifecycleOwner, Observer { accountEntities ->
                accounts = accountEntities
                adapter!!.setAccounts(accounts)
                toolbar!!.title = "Баланс: " + getBalance(accounts) + " BYN"
            })
    }

    private fun getBalance(accounts: List<AccountEntity>): Double {
        var sum = 0.0
        for (accountEntity in accounts) {
            sum += accountEntity.balance
        }
        return sum
    }

    companion object {
        fun newInstance(): AccountsFragment {
            return AccountsFragment()
        }
    }
}