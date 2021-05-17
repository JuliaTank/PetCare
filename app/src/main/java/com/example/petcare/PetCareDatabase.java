package com.example.petcare;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {User.class,Offer.class}, version = 8,exportSchema = false)
public abstract class PetCareDatabase extends RoomDatabase {

    private static PetCareDatabase instance;

    public abstract OfferDao offerDao();
    public abstract UserDao userDao();

    public static synchronized PetCareDatabase getInstance(Context context)
    {
        if(instance==null)
        {
            instance = Room.databaseBuilder(context,PetCareDatabase.class,"petcare_database").fallbackToDestructiveMigration().build();

        }
        return  instance;
    }
}
