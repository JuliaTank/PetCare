package com.example.petcare.view_models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.petcare.Offer;
import com.example.petcare.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
//displaying all offers view model
public class MainActivity2ViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private Repository repository;

    public MainActivity2ViewModel(Application app){
        super(app);
        repository = Repository.getInstance(app);
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public ArrayList<Offer> getOffers(){
        isLoading.setValue(true);
       try{
           ArrayList<Offer> offers = (ArrayList<Offer>) repository.getOffers();
           isLoading.setValue(false);
           return offers;
       }
       catch (Exception e)
       {
           e.printStackTrace();
           isLoading.setValue(false);
       }
        isLoading.setValue(false);
       return null;

    }

    public ArrayList<Offer> getOffers(String search) throws ExecutionException, InterruptedException {
        return (ArrayList<Offer>) repository.getOffers(search);
    }


}
