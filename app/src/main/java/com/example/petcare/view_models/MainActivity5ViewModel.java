package com.example.petcare.view_models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.petcare.Offer;
import com.example.petcare.Repository;
import com.example.petcare.User;

//display single offer view model
public class MainActivity5ViewModel extends AndroidViewModel {

    private MutableLiveData<String> error = new MutableLiveData<>();
    private Repository repository;

    public MainActivity5ViewModel(Application app){
        super(app);
        repository = Repository.getInstance(app);
    }
    public LiveData<String> getError(){return error;}


    public User getUser(int id)  {
        try{
            return repository.getUser(id);
        }
        catch (Exception e){
            e.printStackTrace();
            error.postValue(e.getMessage());
        }
      return  null;
    }
    public void delete(Offer offer){
        repository.deleteOffer(offer);
    }
}
