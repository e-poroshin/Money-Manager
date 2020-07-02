package com.eugene_poroshin.money_manager.operations

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.fragments.FragmentCommunicator
import com.eugene_poroshin.money_manager.repo.database.Operation
import java.util.*

class OperationsAdapter(
    operationList: List<Operation>,
    communication: FragmentCommunicator
) : RecyclerView.Adapter<OperationsAdapter.RecyclerViewHolder>() {

    private var operations: List<Operation>?
    private val communicator: FragmentCommunicator
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.operation_list_item, parent, false)
        return RecyclerViewHolder(
            view,
            communicator
        )
    }

    override fun onBindViewHolder(
        holder: RecyclerViewHolder,
        position: Int
    ) {
        when (operations!![position].category?.id) {
            1 -> holder.imageViewIcon.setImageResource(R.drawable.group_18)
            2 -> holder.imageViewIcon.setImageResource(R.drawable.group_19)
            3 -> holder.imageViewIcon.setImageResource(R.drawable.group_20)
            4 -> holder.imageViewIcon.setImageResource(R.drawable.group_21)
            5 -> holder.imageViewIcon.setImageResource(R.drawable.group_22)
            6 -> holder.imageViewIcon.setImageResource(R.drawable.group_23)
            7 -> holder.imageViewIcon.setImageResource(R.drawable.group_24)
            8 -> holder.imageViewIcon.setImageResource(R.drawable.group_25)
            9 -> holder.imageViewIcon.setImageResource(R.drawable.group_29)
            else -> holder.imageViewIcon.setImageResource(R.drawable.group_26)
        }
        holder.textViewName.text = operations!![position].category?.name
        holder.textViewAccount.text = operations!![position].account?.name
        holder.textViewCurrency.text = operations!![position].account?.currency
        holder.textViewSum.text = operations!![position].operationEntity?.sum.toString()
        if (operations!![position].operationEntity
                ?.type == OperationType.CONSUMPTION
        ) {
            holder.textViewSum.setTextColor(
                ContextCompat.getColor(
                    holder.textViewSum.context,
                    R.color.operation_consumption
                )
            )
            holder.textViewCurrency.setTextColor(
                ContextCompat.getColor(
                    holder.textViewCurrency.context,
                    R.color.operation_consumption
                )
            )
        } else if (operations!![position].operationEntity?.type == OperationType.INCOME) {
            holder.textViewSum.setTextColor(
                ContextCompat.getColor(
                    holder.textViewSum.context,
                    R.color.operation_income
                )
            )
            holder.textViewCurrency.setTextColor(
                ContextCompat.getColor(
                    holder.textViewCurrency.context,
                    R.color.operation_income
                )
            )
        }
    }

    override fun getItemCount(): Int {
        return if (operations != null) {
            operations!!.size
        } else 0
    }

    fun setOperations(operationList: List<Operation>?) {
        operations = operationList
        notifyDataSetChanged()
    }

    inner class RecyclerViewHolder(
        itemView: View,
        mCommunicator: FragmentCommunicator
    ) : RecyclerView.ViewHolder(itemView) {
        val imageViewIcon: ImageView = itemView.findViewById(R.id.itemOperationImageViewIcon)
        val textViewName: TextView = itemView.findViewById(R.id.itemOperationTextViewName)
        val textViewAccount: TextView = itemView.findViewById(R.id.itemOperationTextViewAccount)
        val textViewSum: TextView = itemView.findViewById(R.id.itemOperationTextViewSum)
        val textViewCurrency: TextView = itemView.findViewById(R.id.itemOperationTextViewCurrency)
        private val mCommunication: FragmentCommunicator = mCommunicator

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                val text = operations!![position].category?.name
                mCommunication.onItemClickListener(text)
            }
        }
    }

    init {
        operations = ArrayList(operationList)
        communicator = communication
    }
}