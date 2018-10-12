package com.example.toshiba.mymovie.retrofit;

import com.example.toshiba.mymovie.model.ResponseMovie;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiServices {
    @GET("search/movie?api_key=221fbf2bcc939e0de03af53af4e1744a")
    Call<ResponseMovie> getMovieSearch(@Query("query") String name, @Query("language") String language);

    @GET("movie/now_playing?api_key=221fbf2bcc939e0de03af53af4e1744a")
    Call<ResponseMovie> getMovieUpcoming(@Query("language") String language);


    @GET("movie/upcoming?api_key=221fbf2bcc939e0de03af53af4e1744a")
    Call<ResponseMovie> getMoviePlaying(@Query("language") String language);
}
