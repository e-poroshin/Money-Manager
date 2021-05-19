package com.eugene_poroshin.money_manager.ui.accounts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.databinding.AccountListItemBinding
import com.eugene_poroshin.money_manager.repo.database.AccountEntity
import com.eugene_poroshin.money_manager.ui.Callback

class AccountsAdapter(
    private var accounts: List<AccountEntity>,
    private val callback: Callback
) : RecyclerView.Adapter<AccountsAdapter.AccountViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.account_list_item, parent, false)
        return AccountViewHolder(view, callback)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        when (accounts[position].id) {
            ACCOUNT_TYPE_CASH -> holder.imageViewIcon.setImageResource(R.drawable.group_28)
            ACCOUNT_TYPE_CARD -> holder.imageViewIcon.setImageResource(R.drawable.group_27)
            else -> holder.imageViewIcon.setImageResource(R.drawable.group_29)
            //на такой случай хорошо бы иметь константы, enum или sealed класс - ok?
        }
        holder.textViewName.text = accounts[position].name
        holder.textViewBalance.text = accounts[position].balance.toString()
        holder.textViewCurrency.text = accounts[position].currency
    }

    override fun getItemCount(): Int {
        return accounts.size
    }

    fun setAccounts(accountList: List<AccountEntity>) {
        accounts = accountList
        notifyDataSetChanged()
        //вот здесь как раз было бы нормально переопределить set у accounts и выставить его наружу
        //setAccounts больше джавовский кодстайл
    }

    inner class AccountViewHolder(
        itemView: View,
        private val mCommunicator: Callback
        ) : RecyclerView.ViewHolder(itemView) {

        private var binding: AccountListItemBinding = AccountListItemBinding.bind(itemView)

        val imageViewIcon: ImageView = binding.itemImageViewIcon
        val textViewName: TextView = binding.itemTextViewName
        val textViewBalance: TextView = binding.itemTextViewBalance
        val textViewCurrency: TextView = binding.itemTextViewCurrency

        init {
            itemView.setOnClickListener {
                val accountEntity = accounts[adapterPosition]
                mCommunicator.onItemAccountClick(accountEntity)
            }
        }
    }

    companion object {
        const val ACCOUNT_TYPE_CASH = 0
        const val ACCOUNT_TYPE_CARD = 1
    }
}