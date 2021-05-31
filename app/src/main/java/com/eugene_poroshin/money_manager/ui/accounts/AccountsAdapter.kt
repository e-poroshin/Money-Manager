package com.eugene_poroshin.money_manager.ui.accounts

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.databinding.AccountListItemBinding
import com.eugene_poroshin.money_manager.repo.database.AccountEntity

class AccountsAdapter(
    private val onAccountItemClick: OnAccountItemClick
) : RecyclerView.Adapter<AccountsAdapter.AccountViewHolder>() {

    var accounts: List<AccountEntity> = emptyList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val accountListItemBinding = AccountListItemBinding.inflate(layoutInflater, parent, false)
        return AccountViewHolder(accountListItemBinding, onAccountItemClick)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        when (accounts[position].id) {
            AccountType.CASH.id -> holder.imageViewIcon.setImageResource(R.drawable.group_28)
            AccountType.CARD.id -> holder.imageViewIcon.setImageResource(R.drawable.group_27)
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

    inner class AccountViewHolder(
        binding: AccountListItemBinding,
        private val onItemClick: OnAccountItemClick
        ) : RecyclerView.ViewHolder(binding.root) {

        val imageViewIcon: ImageView = binding.itemImageViewIcon
        val textViewName: TextView = binding.itemTextViewName
        val textViewBalance: TextView = binding.itemTextViewBalance
        val textViewCurrency: TextView = binding.itemTextViewCurrency

        init {
            itemView.setOnClickListener {
                val accountEntity = accounts[adapterPosition]
                onItemClick.onItemClick(accountEntity)
            }
        }
    }

    enum class AccountType(val id: Int) {
        CASH(0),
        CARD(1)
    }

    interface OnAccountItemClick {
        fun onItemClick(accountEntity: AccountEntity)
    }
}