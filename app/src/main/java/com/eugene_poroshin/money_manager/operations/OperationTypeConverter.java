package com.eugene_poroshin.money_manager.operations;

import androidx.room.TypeConverter;

public class OperationTypeConverter {

    @TypeConverter
    public static String fromTypeToString(OperationType type) {
        if (type == null)
            return null;
        return type.toString();
    }

    @TypeConverter
    public static OperationType fromStringToType(String type) {
        if (type.equals(OperationType.CONSUMPTION.toString())) {
            return OperationType.CONSUMPTION;
        } else if (type.equals(OperationType.INCOME.toString())) {
            return OperationType.INCOME;
        } else {
            throw new IllegalArgumentException("Could not recognize type");
        }
    }
}
