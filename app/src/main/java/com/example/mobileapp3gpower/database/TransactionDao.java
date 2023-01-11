package com.example.mobileapp3gpower.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface TransactionDao {
    @Query("SELECT * FROM `transaction`")
    List<Transaction> getAll();

    @Query("SELECT * FROM `transaction` WHERE transactionId IN (:transactionIds)")
    List<Transaction> loadAllByIds(int[] transactionIds);

    @Query("SELECT * FROM `transaction` WHERE transactionId = :transactionId LIMIT 1")
    Transaction findById(int transactionId);

    @Query("SELECT * FROM `transaction` WHERE userId = :userId")
    List<Transaction> findByUser(int userId);

    @Insert
    void insertAll(Transaction... Transaction);

    @Update
    void update(Transaction transaction);

    @Delete
    void delete(Transaction transaction);
}
