package com.example.petcare;

import com.google.android.gms.common.api.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder().baseUrl("https://catfact.ninja/").addConverterFactory(GsonConverterFactory.create());

    private  static Retrofit retrofit = retrofitBuilder.build();

    private static FactApi factApi = retrofit.create(FactApi.class);

    public static FactApi getFactApi()
    {
        return factApi;
    }
}
