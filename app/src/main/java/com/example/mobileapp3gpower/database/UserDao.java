package com.example.mobileapp3gpower.database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDao {
    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("SELECT * FROM user WHERE userId IN (:userIds)")
    List<User> loadAllByIds(int[] userIds);

    @Query("SELECT * FROM user WHERE email = :email AND password = :password AND role = 'user' LIMIT 1")
    User findByEmailAndPasswordUser(String email, String password);

    @Query("SELECT * FROM user WHERE email = :email AND password = :password AND role = 'admin' LIMIT 1")
    User findByEmailAndPasswordAdmin(String email, String password);

    @Query("SELECT * FROM user WHERE email = :email LIMIT 1")
    User findByEmail(String email);

    @Query("SELECT * FROM user WHERE userId = :userId LIMIT 1")
    User findById(int userId);

    @Query("SELECT * FROM user WHERE role = 'admin' LIMIT 1")
    User findAdmin();

    @Query("SELECT * FROM user WHERE role = 'user' LIMIT 1")
    User findUser();

    @Insert
    void insertAll(User... users);

    @Update
    void update(User user);

    @Delete
    void delete(User user);
}