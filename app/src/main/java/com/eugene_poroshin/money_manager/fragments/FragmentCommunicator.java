package com.eugene_poroshin.money_manager.fragments;

import com.eugene_poroshin.money_manager.repo.database.AccountEntity;

public interface FragmentCommunicator {
    void onItemClickListener(String categoryName);
    void onItemAccountClickListener(AccountEntity accountEntity);
}
