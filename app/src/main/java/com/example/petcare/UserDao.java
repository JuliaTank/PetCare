package com.example.petcare;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface UserDao {

   @Insert
    void insert(User user);

   @Query("SELECT * FROM user_table")
    List<User>  getAllUsers();

    @Query("SELECT * FROM user_table WHERE username = :user_name")
    User getUser(String user_name);

    @Query("SELECT * FROM user_table WHERE id = :id")
    User getUser(int id);

}
