package com.example.petcare.view_models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.petcare.Offer;
import com.example.petcare.Repository;
//new offer view model
public class MainActivity4ViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>(false);
    private MutableLiveData<String> error = new MutableLiveData<>();
    private Repository repository;

    public MainActivity4ViewModel(Application app){
        super(app);
        repository = Repository.getInstance(app);
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }
    public LiveData<String> getError(){return error;}

    public void addOffer(Offer offer){
        try{
            repository.addOffer(offer);
        }
        catch (Exception e){
            e.printStackTrace();
            error.setValue(e.getMessage());
        }

    }
}
