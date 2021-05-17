package com.example.petcare;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface OfferDao {

    @Insert
    void insert(Offer offer);

    @Update
    void update(Offer offer);

    @Delete
    void delete(Offer offer);

    @Query("SELECT * FROM offer_table")
    List<Offer> getAllOffers();

    @Query("SELECT * FROM offer_table WHERE title LIKE :search OR localization LIKE :search")
    List<Offer> getOffers(String search);

}
