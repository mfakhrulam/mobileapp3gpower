package com.example.mobileapp3gpower.database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {
    @PrimaryKey(autoGenerate = true)
    public int userId;

    @ColumnInfo(name = "email")
    public String email;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "password")
    public String password;

    @ColumnInfo(name = "role")
    public String role;

    public User() {
    }

    public User(String email, String name, String password, String role) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }

    public User(int userId, String email, String name, String password, String role) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.password = password;
        this.role = role;
    }
}