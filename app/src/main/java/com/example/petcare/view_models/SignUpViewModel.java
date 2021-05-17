package com.example.petcare.view_models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.petcare.Repository;
import com.example.petcare.User;

public class SignUpViewModel extends AndroidViewModel {

    private Repository repository;


    public SignUpViewModel(Application app){
        super(app);
        repository = Repository.getInstance(app);
    }

    public void signUp(User user) throws Exception {

    if(repository.getUser(user.getUsername())!=null){
        throw new Exception("Username taken");
    }
        repository.addUser(user);
    }
}
