package com.eugene_poroshin.money_manager.ui.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.eugene_poroshin.money_manager.R
import com.eugene_poroshin.money_manager.repo.database.CategoryEntity

class CategoriesAdapter(
    private val onCategoryItemClick: OnCategoryItemClick
) : RecyclerView.Adapter<CategoriesAdapter.RecyclerViewHolder>() {

    var categories: List<CategoryEntity> = emptyList()
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewHolder {
        val view = LayoutInflater.from(parent.context) //todo binding
            .inflate(R.layout.category_list_item, parent, false)
        return RecyclerViewHolder(
            view,
            onCategoryItemClick
        )
    }

    override fun onBindViewHolder(
        holder: RecyclerViewHolder,
        position: Int
    ) {
        when (categories[position].id) {
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
        holder.textViewName.text = categories[position].name
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    inner class RecyclerViewHolder(
        itemView: View,
        private val onItemClick: OnCategoryItemClick
    ) : RecyclerView.ViewHolder(itemView) {

        val imageViewIcon: ImageView = itemView.findViewById(R.id.itemImageView)
        val textViewName: TextView = itemView.findViewById(R.id.itemTextView)

        init {
            itemView.setOnClickListener {
                val text = textViewName.text.toString()
                onItemClick.onItemClick(text)
            }
        }
    }

    interface OnCategoryItemClick {
        fun onItemClick(categoryName: String)
    }
}