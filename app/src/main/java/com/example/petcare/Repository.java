package com.example.petcare;

import android.app.Application;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;

import androidx.core.os.HandlerCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Dao;

import com.google.firebase.database.DatabaseReference;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;


public class Repository {

    private static Repository instance;
    private final UserDao userDao;
    private final OfferDao offerDao;

    private  MutableLiveData<CatFact> fact;

    private final ExecutorService executorService;

    private Repository(Application application){

        PetCareDatabase database = PetCareDatabase.getInstance(application);

        userDao = database.userDao();
        offerDao = database.offerDao();

        executorService = Executors.newFixedThreadPool(2);

        fact = new MutableLiveData<>();
        Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());
        //user dummy data
       //addUser(new User("test","test","+4500000000","test@gmail.com"));
    }

    public static synchronized Repository getInstance(Application application)
    {
        if(instance==null){
            instance = new Repository(application);
        }
        return instance;
    }

    //offers methods
    public List<Offer> getOffers() throws ExecutionException, InterruptedException {
         return new GetOffersAsync(offerDao).execute().get();
    }

    public void addOffer(Offer offer){new InsertOfferAsync(offerDao).execute(offer);}

    public void deleteOffer(Offer offer){
        new DeleteOfferAsync(offerDao).execute(offer);
    }

    public List<Offer> getOffers(String search) throws ExecutionException, InterruptedException {
        return  new GetSearchedOffersAsync(offerDao).execute("%"+search+"%").get();
    }


    //users methods

    public void addUser(User user){ new InsertUserAsync(userDao).execute(user); }

    public LiveData<User> getUser(String username) throws ExecutionException, InterruptedException {

       User user = new GetUserAsync(userDao).execute(username).get();
        MutableLiveData<User> userLiveData = new MutableLiveData<>();
        if (user != null) {
            userLiveData.setValue(user);
            return userLiveData;
        }
        return null;
    }

    public User getUser(int id) throws ExecutionException, InterruptedException {
        User user = new GetUserByIdAsync(userDao).execute(id).get();
        return user;
    }

    //random fact API
    public LiveData<CatFact> getFact(){

        FactApi factApi = ServiceGenerator.getFactApi();
        Call<CatFact> call = factApi.getFact();
        call.enqueue(new Callback<CatFact>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<CatFact> call, Response<CatFact> response) {
                if(response.isSuccessful()){
                    fact.setValue(response.body());
                }
            }
            @EverythingIsNonNull

            @Override
            public void onFailure(Call<CatFact> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });
        return fact;
    }


    //Async Task classes
    private static class  InsertOfferAsync extends AsyncTask<Offer,Void,Void>
    {
        private OfferDao offerDao;
        private InsertOfferAsync(OfferDao offerDao){
            this.offerDao =offerDao;
        }

        @Override
        protected Void doInBackground(Offer... offers) {
            offerDao.insert(offers[0]);
            return null;
        }
    }

    private static class DeleteOfferAsync extends AsyncTask<Offer,Void,Void>{

        private OfferDao offerDao;
        private DeleteOfferAsync(OfferDao offerDao){this.offerDao = offerDao;}

        @Override
        protected Void doInBackground(Offer... offers) {
            offerDao.delete(offers[0]);
            return null;
        }
    }

    private static class  InsertUserAsync extends AsyncTask<User,Void,Void>
    {
        private UserDao userDao;
        private InsertUserAsync(UserDao userDao){
            this.userDao =userDao;
        }

        @Override
        protected Void doInBackground(User... users) {
            userDao.insert(users[0]);
            return null;
        }
    }

    private static class GetUserAsync extends AsyncTask<String,Void,User>
    {
        private UserDao userDao;
        private GetUserAsync(UserDao userDao){
            this.userDao =userDao;
        }
        @Override
        protected User doInBackground(String... strings) {
            return userDao.getUser(strings[0]);
        }
    }
    private static class GetUserByIdAsync extends AsyncTask<Integer,Void,User>
    {
        private UserDao userDao;
        private GetUserByIdAsync(UserDao userDao){
            this.userDao =userDao;
        }

        @Override
        protected User doInBackground(Integer... integers) {
            return userDao.getUser(integers[0]);
        }
    }

    private static class GetOffersAsync extends AsyncTask<Void,Void,List<Offer>>
    {
        private OfferDao offerDao;
        private GetOffersAsync(OfferDao offerDao){
            this.offerDao =offerDao;
        }
        @Override
        protected List<Offer> doInBackground(Void...voids) {
            List<Offer> offers = offerDao.getAllOffers();
            System.out.println(offers.toString());
            return offers;
        }
    }
    private static class GetSearchedOffersAsync extends AsyncTask<String,Void,List<Offer>>
    {
        private OfferDao offerDao;
        private GetSearchedOffersAsync(OfferDao offerDao){
            this.offerDao =offerDao;
        }
        @Override
        protected List<Offer> doInBackground(String...strings) {
            List<Offer> offers = offerDao.getOffers(strings[0]);
            System.out.println(offers.toString());
            return offers;
        }
    }
}
