package com.eugene_poroshin.money_manager.ui.accounts

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.ui.FragmentCommunicator
import com.eugene_poroshin.money_manager.repo.database.AccountEntity

class AccountsAdapter(
    accountList: List<AccountEntity>,
    //в конструкторе всегда передается пустой лист, действительно ли это необходимо?
    communication: FragmentCommunicator
    // можно прямо в конструкторе добавить private val, и не создовать дополнительное свойство + определение в  init
) : RecyclerView.Adapter<AccountsAdapter.RecyclerViewHolder>() {

    private var accounts: List<AccountEntity>?
    //зачем нулабельность? логически, список аккаунтов существует всегда, просто иногда он пуст
    private val communicator: FragmentCommunicator
    //можно избавиться добавив private val у communication

    init {
        accounts = ArrayList(accountList)
        //зачем оборачивать?
        communicator = communication
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.account_list_item, parent, false)
        return RecyclerViewHolder(view, communicator)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        when (accounts!![position].id) {
            1 -> holder.imageViewIcon.setImageResource(R.drawable.group_28)
            2 -> holder.imageViewIcon.setImageResource(R.drawable.group_27)
            else -> holder.imageViewIcon.setImageResource(R.drawable.group_29)
            //на такой случай хорошо бы иметь константы, enum или sealed класс
        }
        holder.textViewName.text = accounts!![position].name
        holder.textViewBalance.text = accounts!![position].balance.toString()
        holder.textViewCurrency.text = accounts!![position].currency
        //в случаях, когда есть несколько типов viewHolder, bind логика выносится в класс viewHolder в метод bind()
        //здесь bind логика в onBindViewHolder - не ошибка, т.к. viewHolder один
        //!! - плохо
    }

    override fun getItemCount(): Int {
        return if (accounts != null) {
            accounts!!.size
        } else 0
        //посмотри про работу с нулабельными типами в котлине accounts?.size ?: 0
    }

    fun setAccounts(accountList: List<AccountEntity>?) {
        accounts = accountList
        notifyDataSetChanged()
        //вот здесь как раз было бы нормально переопределить set у accounts и выставить его наружу
        //setAccounts больше джавовский кодстайл
    }

    inner class RecyclerViewHolder(itemView: View, mCommunicator: FragmentCommunicator) :
        RecyclerView.ViewHolder(itemView) {
        //почему здесь не используешь viewBinding?
        //нейминг, это скорее AccountViewHolder

        val imageViewIcon: ImageView = itemView.findViewById(R.id.itemImageViewIcon)
        val textViewName: TextView = itemView.findViewById(R.id.itemTextViewName)
        val textViewBalance: TextView = itemView.findViewById(R.id.itemTextViewBalance)
        val textViewCurrency: TextView = itemView.findViewById(R.id.itemTextViewCurrency)
        private val mCommunication: FragmentCommunicator = mCommunicator
        //можно вынести в конструктор добавив private val к mCommunicator

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                val accountEntity = accounts!![position]
                //можно было бы сразу использовать adapterPosition
                //!!
                mCommunication.onItemAccountClickListener(accountEntity)
            }
        }
    }
}