package com.eugene_poroshin.money_manager.categories;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eugene_poroshin.money_manager.R;
import com.eugene_poroshin.money_manager.fragments.FragmentCommunicator;
import com.eugene_poroshin.money_manager.repo.database.CategoryEntity;

import java.util.ArrayList;
import java.util.List;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.RecyclerViewHolder> {

    private List<CategoryEntity> categories;
    private FragmentCommunicator communicator;

    public CategoriesAdapter(List<CategoryEntity> categoryList, FragmentCommunicator communication) {
        this.categories = new ArrayList<>(categoryList);
        this.communicator = communication;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_list_item, parent, false);
        return new RecyclerViewHolder(view, communicator);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        if (categories.get(position).getName().equals("Продукты")) {
            holder.imageViewIcon.setImageResource(R.drawable.group_18);
        } else if (categories.get(position).getName().equals("Здоровье")) {
            holder.imageViewIcon.setImageResource(R.drawable.group_19);
        } else if (categories.get(position).getName().equals("Кафе")) {
            holder.imageViewIcon.setImageResource(R.drawable.group_20);
        } else if (categories.get(position).getName().equals("Досуг")) {
            holder.imageViewIcon.setImageResource(R.drawable.group_21);
        }else if (categories.get(position).getName().equals("Транспорт")) {
            holder.imageViewIcon.setImageResource(R.drawable.group_22);
        } else if (categories.get(position).getName().equals("Подарки")) {
            holder.imageViewIcon.setImageResource(R.drawable.group_23);
        } else if (categories.get(position).getName().equals("Покупки")) {
            holder.imageViewIcon.setImageResource(R.drawable.group_24);
        } else if (categories.get(position).getName().equals("Семья")) {
            holder.imageViewIcon.setImageResource(R.drawable.group_25);
        } else if (categories.get(position).getName().equals("Зарплата")) {
            holder.imageViewIcon.setImageResource(R.drawable.group_29);
        } else {
            holder.imageViewIcon.setImageResource(R.drawable.group_26);
        }
        holder.textViewName.setText(categories.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (categories != null) {
            return categories.size();
        } else
            return 0;
    }

    public void setCategories(List<CategoryEntity> categoryList) {
        categories = categoryList;
        notifyDataSetChanged();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewIcon;
        private TextView textViewName;
        private FragmentCommunicator mCommunication;

        public RecyclerViewHolder(@NonNull View itemView, FragmentCommunicator mCommunicator) {
            super(itemView);
            imageViewIcon = itemView.findViewById(R.id.itemImageView);
            textViewName = itemView.findViewById(R.id.itemTextView);
            mCommunication = mCommunicator;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String text = textViewName.getText().toString();
                    mCommunication.onItemClickListener(text);
                }
            });
        }
    }

}
