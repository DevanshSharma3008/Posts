package com.example.admin.posts.network;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by admin on 7/11/2017.
 */

public interface ApiInterface {


    @GET("posts")
    Call<ArrayList<User>> getPosts();


}
