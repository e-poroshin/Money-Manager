package com.eugene_poroshin.money_manager.ui.operations

import androidx.room.TypeConverter

class OperationTypeConverter {

    @TypeConverter
    fun fromTypeToString(type: OperationType?): String? = type?.converterName

    @TypeConverter
    fun fromStringToType(stringType: String?): OperationType? =
        OperationType.values().firstOrNull { type -> type.converterName == stringType }
}

private val OperationType.converterName: String
    get() =
        when (this) {
            OperationType.EXPENSE -> "CONSUMPTION"
            OperationType.INCOME -> "INCOME"
        }