package com.eugene_poroshin.money_manager.accounts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.fragments.FragmentCommunicator
import com.eugene_poroshin.money_manager.repo.database.AccountEntity

class AccountsAdapter(accountList: MutableList<AccountEntity>, communication: FragmentCommunicator) : RecyclerView.Adapter<AccountsAdapter.RecyclerViewHolder>() {

    private var accounts: MutableList<AccountEntity>?
    private val communicator: FragmentCommunicator

    init {
        accounts = ArrayList(accountList)
        communicator = communication
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.account_list_item, parent, false)
        return RecyclerViewHolder(view, communicator)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        if (accounts!![position].name == "Наличные") {
            holder.imageViewIcon.setImageResource(R.drawable.group_28)
        } else if (accounts!![position].name == "Карта") {
            holder.imageViewIcon.setImageResource(R.drawable.group_27)
        } else {
            holder.imageViewIcon.setImageResource(R.drawable.group_29)
        }
        holder.textViewName.text = accounts!![position].name
        holder.textViewBalance.text = accounts!![position].balance.toString()
        holder.textViewCurrency.text = accounts!![position].currency
    }

    override fun getItemCount(): Int {
        return if (accounts != null) {
            accounts!!.size
        } else 0
    }

    fun setAccounts(accountList: MutableList<AccountEntity>?) {
        accounts = accountList
        notifyDataSetChanged()
    }

    inner class RecyclerViewHolder(itemView: View, mCommunicator: FragmentCommunicator) :
        RecyclerView.ViewHolder(itemView) {

        val imageViewIcon: ImageView = itemView.findViewById(R.id.itemImageViewIcon)
        val textViewName: TextView = itemView.findViewById(R.id.itemTextViewName)
        val textViewBalance: TextView = itemView.findViewById(R.id.itemTextViewBalance)
        val textViewCurrency: TextView = itemView.findViewById(R.id.itemTextViewCurrency)
        private val mCommunication: FragmentCommunicator = mCommunicator

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                val accountEntity = accounts!![position]
                mCommunication.onItemAccountClickListener(accountEntity)
            }
        }
    }
}