package com.randstad.nasaapp.network.api;

import com.randstad.nasaapp.model.NasaImage;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface Methods {
    @Headers({"Content-Type: application/json"})
    @GET("EPIC/api/natural")
    Call<ArrayList<NasaImage>> getImageList(@Query("api_key") String apiKey);
}