package com.example.petcare;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "user_table")
public class User implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String username;
    private String password;
    private String phone_nr;
    private String email;

    public User(String username, String password,String phone_nr,String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone_nr = phone_nr;
    }

    public User(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone_nr() {
        return phone_nr;
    }

    public void setPhone_nr(String phone_nr) {
        this.phone_nr = phone_nr;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
