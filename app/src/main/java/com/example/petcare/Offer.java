package com.example.petcare;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "offer_table")
public class Offer implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String description;
    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] photo;
    private String title;
    //price means money user offers for taking care of pet
    private  double price;
    private String dateFrom;
    private String dateTo;
    private String localization;
    private int offer_userID;


    public Offer(String description, byte[] photo, String title, double price, String dateFrom, String dateTo, String localization,int offer_userID) {
        this.description = description;
        this.photo = photo;
        this.title = title;
        this.price = price;
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.localization = localization;
        this.offer_userID = offer_userID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte[] getPhoto() {
        return photo;
    }

    public void setPhoto(byte[] photo) {
        this.photo = photo;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(String dateFrom) {
        this.dateFrom = dateFrom;
    }

    public String getDateTo() {
        return dateTo;
    }

    public void setDateTo(String dateTo) {
        this.dateTo = dateTo;
    }

    public String getLocalization() {
        return localization;
    }

    public void setLocalization(String localization) {
        this.localization = localization;
    }

    public void setOffer_userID(int offer_userID) {
        this.offer_userID = offer_userID;
    }

    public int getOffer_userID() {
        return offer_userID;
    }
}
