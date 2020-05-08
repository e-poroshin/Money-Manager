package com.eugene_poroshin.money_manager.accounts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.eugene_poroshin.money_manager.R;
import com.eugene_poroshin.money_manager.fragments.FragmentCommunicator;
import com.eugene_poroshin.money_manager.repo.database.AccountEntity;
import com.eugene_poroshin.money_manager.repo.database.CategoryEntity;

import java.util.ArrayList;
import java.util.List;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.RecyclerViewHolder> {

    private List<AccountEntity> accounts;
    private FragmentCommunicator communicator;

    public AccountsAdapter(List<AccountEntity> accountList, FragmentCommunicator communication) {
        this.accounts = new ArrayList<>(accountList);
        this.communicator = communication;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_list_item, parent, false);
        return new RecyclerViewHolder(view, communicator);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        if (accounts.get(position).getName().equals("Наличные")) {
            holder.imageViewIcon.setImageResource(R.drawable.group_28);
        } else if (accounts.get(position).getName().equals("Карта")) {
            holder.imageViewIcon.setImageResource(R.drawable.group_27);
        } else {
            holder.imageViewIcon.setImageResource(R.drawable.group_29);
        }
        holder.textViewName.setText(accounts.get(position).getName());
        holder.textViewBalance.setText(String.valueOf(accounts.get(position).getBalance()));
        holder.textViewCurrency.setText(accounts.get(position).getCurrency());
    }

    @Override
    public int getItemCount() {
        if (accounts != null) {
            return accounts.size();
        } else
            return 0;
    }

    public void setAccounts(List<AccountEntity> accountList) {
        accounts = accountList;
        notifyDataSetChanged();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageViewIcon;
        private TextView textViewName;
        private TextView textViewBalance;
        private TextView textViewCurrency;
        private FragmentCommunicator mCommunication;

        public RecyclerViewHolder(@NonNull View itemView, FragmentCommunicator mCommunicator) {
            super(itemView);
            imageViewIcon = itemView.findViewById(R.id.itemImageViewIcon);
            textViewName = itemView.findViewById(R.id.itemTextViewName);
            textViewBalance = itemView.findViewById(R.id.itemTextViewBalance);
            textViewCurrency = itemView.findViewById(R.id.itemTextViewCurrency);
            mCommunication = mCommunicator;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    AccountEntity accountEntity = accounts.get(position);
                    mCommunication.onItemAccountClickListener(accountEntity);
                }
            });
        }
    }

}
