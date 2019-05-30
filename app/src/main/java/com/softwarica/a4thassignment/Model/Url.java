package com.softwarica.a4thassignment.Model;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Url {
    //this is the URL to connect with the API
public static String BASE_URL="http://10.0.2.2:3000/4thassignment/";
    API api;
//this method will return API with Base URL
    public API createInstanceofRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = retrofit.create(API.class);
        return api;
    }
}
