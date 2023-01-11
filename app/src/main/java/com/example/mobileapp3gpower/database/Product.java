package com.example.mobileapp3gpower.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Product {
    @PrimaryKey(autoGenerate = true)
    public int productId;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "stock")
    public int stock;

    @ColumnInfo(name = "price")
    public int price;

    @ColumnInfo(name = "warranty")
    public int warranty;

    public Product() {
    }

    public Product(String name, int stock, int price, int warranty) {
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.warranty = warranty;
    }

    public Product(int productId, String name, int stock, int price, int warranty) {
        this.productId = productId;
        this.name = name;
        this.stock = stock;
        this.price = price;
        this.warranty = warranty;
    }
}