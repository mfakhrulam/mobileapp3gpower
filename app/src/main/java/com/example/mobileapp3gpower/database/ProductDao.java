package com.example.mobileapp3gpower.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT * FROM product")
    List<Product> getAll();

    @Query("SELECT * FROM product WHERE productId IN (:productIds)")
    List<Product> loadAllByIds(int[] productIds);

    @Query("SELECT * FROM product WHERE productId = :productId LIMIT 1")
    Product findById(int productId);

    @Insert
    void insertAll(Product... products);

    @Update
    void update(Product product);

    @Delete
    void delete(Product product);
}
