package com.example.mobileapp3gpower.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity
public class Transaction {
    @PrimaryKey(autoGenerate = true)
    public int transactionId;

    @ColumnInfo(name = "userId")
    public int userId;

    @ColumnInfo(name = "productId")
    public int productId;

    @ColumnInfo(name = "date")
    public Date date;

    @ColumnInfo(name = "amounts")
    public int amount;

    @ColumnInfo(name = "total")
    public int total;

    public Transaction(int userId, int productId, Date date, int amount, int total) {
        this.userId = userId;
        this.productId = productId;
        this.date = date;
        this.amount = amount;
        this.total = total;
    }
}
