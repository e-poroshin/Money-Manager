package com.eugene_poroshin.money_manager.ui.operations

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.databinding.OperationListItemBinding
import com.eugene_poroshin.money_manager.repo.database.Operation

class OperationsAdapter(
        private var operations: List<Operation>
) : RecyclerView.Adapter<OperationsAdapter.OperationViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OperationViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemPersonBinding = OperationListItemBinding.inflate(layoutInflater, parent, false)
        return OperationViewHolder(itemPersonBinding)
    }

    override fun onBindViewHolder(
            holder: OperationViewHolder,
            position: Int
    ) = holder.bind(operations[position])

    override fun getItemCount(): Int = operations.size

    fun setOperations(operationList: List<Operation>) {
        operations = operationList
        notifyDataSetChanged()
    }

    class OperationViewHolder(
            private val itemViewBinding: OperationListItemBinding
    ) : RecyclerView.ViewHolder(itemViewBinding.root) {

        fun bind(operation: Operation) {

            itemViewBinding.itemOperationImageViewIcon.setImageResource(when (operation.category?.id) {
                1 -> R.drawable.group_18
                2 -> R.drawable.group_19
                3 -> R.drawable.group_20
                4 -> R.drawable.group_21
                5 -> R.drawable.group_22
                6 -> R.drawable.group_23
                7 -> R.drawable.group_24
                8 -> R.drawable.group_25
                9 -> R.drawable.group_29
                else -> R.drawable.group_26
            })

            itemViewBinding.itemOperationTextViewName.text = operation.category?.name
            itemViewBinding.itemOperationTextViewAccount.text = operation.account?.name
            itemViewBinding.itemOperationTextViewCurrency.text = operation.account?.currency
            itemViewBinding.itemOperationTextViewSum.text = operation.operationEntity?.sum.toString()

            @ColorRes
            val colorRes = when (operation.operationEntity?.type) {
                OperationType.EXPENSE -> R.color.operation_consumption
                OperationType.INCOME -> R.color.operation_income
                //todo remove elsed
                else -> R.color.operation_income
            }
            @ColorInt
            val colorInt = ContextCompat.getColor(itemViewBinding.itemOperationTextViewSum.context, colorRes)

            itemViewBinding.itemOperationTextViewSum.setTextColor(colorInt)
            itemViewBinding.itemOperationTextViewCurrency.setTextColor(colorInt)
        }
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
//                val text = operations!![position].category?.name
//                communication.onItemClickListener(text)
            }
        }
    }
}