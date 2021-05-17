package com.example.petcare.view_models;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

import com.example.petcare.Offer;
import com.example.petcare.Repository;
import com.example.petcare.activities.OwnedOffers;

import java.util.ArrayList;

public class OwnedOffersViewModel extends AndroidViewModel {

    private Repository repository;


    public OwnedOffersViewModel(Application app){
        super(app);
        repository = Repository.getInstance(app);
    }

    public ArrayList<Offer> getOffers(){
        try{
            return (ArrayList<Offer>) repository.getOffers();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
