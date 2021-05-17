package com.example.petcare.view_models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.petcare.Repository;
import com.example.petcare.User;

//login view model
public class MainActivityViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private Repository repository;

    public MainActivityViewModel(Application app){
        super(app);
        repository = Repository.getInstance(app);
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }


    public LiveData<User> login(String username, String password) throws Exception {

        isLoading.setValue(true);
       LiveData<User> user = repository.getUser(username);
       if(user==null)
       {
           isLoading.setValue(false);
           throw new Exception("Username not found");
       }
       else if(!user.getValue().getPassword().equals(password))
       {
           isLoading.setValue(false);
           throw new Exception("Wrong password");
       }
       isLoading.setValue(false);
       return user;
    }


}
