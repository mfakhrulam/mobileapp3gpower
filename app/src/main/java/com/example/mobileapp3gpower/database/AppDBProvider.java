package com.example.mobileapp3gpower.database;

import android.content.Context;

import androidx.room.Room;

public class AppDBProvider {
    private static AppDatabase instance;

    public static AppDatabase getInstance(Context context) {
        if(AppDBProvider.instance == null)
        {
            AppDBProvider.instance = Room.databaseBuilder(
                    context, AppDatabase.class, "mobileapp3gpower.db"
            ).allowMainThreadQueries().build();
        }
        return AppDBProvider.instance;
    }
}
