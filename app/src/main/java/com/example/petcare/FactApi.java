package com.example.petcare;

import retrofit2.Call;
import retrofit2.http.GET;

public interface FactApi {
    @GET("fact")
    Call<CatFact> getFact();
}
