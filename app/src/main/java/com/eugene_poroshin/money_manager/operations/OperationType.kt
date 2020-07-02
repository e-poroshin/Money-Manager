package com.eugene_poroshin.money_manager.operations

enum class OperationType(private val text: String) {
    INCOME("INCOME"),
    CONSUMPTION("CONSUMPTION");

    override fun toString(): String {
        return text
    }

}