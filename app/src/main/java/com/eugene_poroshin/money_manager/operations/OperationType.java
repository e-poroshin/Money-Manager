package com.eugene_poroshin.money_manager.operations;

public enum OperationType {
    INCOME("INCOME"),
    CONSUMPTION("CONSUMPTION");

    private final String text;

    OperationType(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }
}
