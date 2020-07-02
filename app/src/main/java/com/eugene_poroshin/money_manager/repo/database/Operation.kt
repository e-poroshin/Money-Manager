package com.eugene_poroshin.money_manager.repo.database

import androidx.room.Embedded
import androidx.room.Relation

class Operation {
    @Embedded
    var operationEntity: OperationEntity? = null

    @Relation(
        parentColumn = "operation_category_id",
        entityColumn = "category_id",
        entity = CategoryEntity::class
    )
    var category: CategoryEntity? = null

    @Relation(
        parentColumn = "operation_account_id",
        entityColumn = "account_id",
        entity = AccountEntity::class
    )
    var account: AccountEntity? = null

}