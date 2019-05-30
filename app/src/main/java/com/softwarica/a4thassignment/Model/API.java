package com.softwarica.a4thassignment.Model;

import java.util.List;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface API {
    //this is the method to create new user through API
    @POST("createuser")
    Call<Void> addNewUser(@Body User user);

    //this method is to login users through API
    @FormUrlEncoded
    @POST("userlogin")
    Call<String> checkUser(@Field("username") String username, @Field("password") String password);

    //this is the method to add new Items through API
    @POST("additem")
    Call<Void> addNewItem(@Body Item item);

    //this is a get method that will get all the data of items from API
    @GET("displayitem")
    Call<List<Item>> getAllItem();

    //this is a post method which will upload pic to server or API
    @Multipart
    @POST("uploadpic")
    Call<ImageFile> uploadImage(@Part MultipartBody.Part body);
}
