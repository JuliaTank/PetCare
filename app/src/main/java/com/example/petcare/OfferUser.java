package com.example.petcare;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

//class handling many to many relationship between users and offers
public class OfferUser {
    @Embedded public User user;
    @Relation(
            parentColumn = "id",
            entityColumn = "offer_userID"
    )
    public List<Offer> offers;
}
