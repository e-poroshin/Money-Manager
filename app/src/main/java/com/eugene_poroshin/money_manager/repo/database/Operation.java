package com.eugene_poroshin.money_manager.repo.database;

import androidx.room.Embedded;
import androidx.room.Relation;

public class Operation {

    @Embedded
    private OperationEntity operationEntity;

    @Relation(parentColumn = "operation_category_id", entityColumn = "category_id", entity = CategoryEntity.class)
    private CategoryEntity category;

    @Relation(parentColumn = "operation_account_id", entityColumn = "account_id", entity = AccountEntity.class)
    private AccountEntity account;

    public void setOperationEntity(OperationEntity operationEntity) {
        this.operationEntity = operationEntity;
    }

    public OperationEntity getOperationEntity() {
        return operationEntity;
    }

    public void setCategory(CategoryEntity category) {
        this.category = category;
    }

    public CategoryEntity getCategory() {
        return category;
    }

    public void setAccount(AccountEntity account) {
        this.account = account;
    }

    public AccountEntity getAccount() {
        return account;
    }
}
