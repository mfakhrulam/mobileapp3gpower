package com.example.mobileapp3gpower.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {User.class, Product.class, Transaction.class}, version = 1)
@TypeConverters({DateConventer.class})
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
    public abstract ProductDao productDao();
    public abstract TransactionDao transactionDao();
}
