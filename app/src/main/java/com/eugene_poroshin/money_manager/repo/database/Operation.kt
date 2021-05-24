package com.eugene_poroshin.money_manager.repo.database

import androidx.room.Embedded
import androidx.room.Relation

class Operation {
    @Embedded
    lateinit var operationEntity: OperationEntity

    @Relation(
        parentColumn = "operation_category_id",
        entityColumn = "category_id",
        entity = CategoryEntity::class
    )
    lateinit var category: CategoryEntity

    @Relation(
        parentColumn = "operation_account_id",
        entityColumn = "account_id",
        entity = AccountEntity::class
    )
    lateinit var account: AccountEntity

}