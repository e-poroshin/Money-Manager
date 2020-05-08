package com.eugene_poroshin.money_manager.repo.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.eugene_poroshin.money_manager.operations.OperationType;
import com.eugene_poroshin.money_manager.operations.OperationTypeConverter;

@Entity(tableName = "operations")
public class OperationEntity {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "operation_id")
    private int id;
    @ColumnInfo(name = "operation_category_id")
    private int categoryId;
    @ColumnInfo(name = "operation_account_id")
    private int accountId;
    @ColumnInfo(name = "operation_date")
    private long date;
    @ColumnInfo(name = "operation_sum")
    private double sum;
    @ColumnInfo(name = "operation_description")
    private String description;
    @ColumnInfo(name = "operation_type")
    @TypeConverters({OperationTypeConverter.class})
    private OperationType type;

    public OperationEntity(int categoryId, int accountId, long date, double sum,
                           String description, OperationType type) {
        this.categoryId = categoryId;
        this.accountId = accountId;
        this.date = date;
        this.sum = sum;
        this.description = description;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public int getAccountId() {
        return accountId;
    }

    public long getDate() {
        return date;
    }

    public double getSum() {
        return sum;
    }

    public String getDescription() {
        return description;
    }

    public OperationType getType() {
        return type;
    }
}
