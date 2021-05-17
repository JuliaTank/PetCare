package com.example.petcare.view_models;

import android.app.Application;

import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.petcare.CatFact;
import com.example.petcare.Repository;
//welcoming activity view model
public class MainActivity3ViewModel  extends AndroidViewModel {

    private Repository repository;

    public MainActivity3ViewModel(Application app){
        super(app);
        repository = Repository.getInstance(app);
    }

    public  LiveData<CatFact> getFact(){
       return repository.getFact();
    }
}
