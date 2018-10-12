package com.example.toshiba.mymovie.retrofit;

public class UtilsApi {
    private static final String BASE_URL_API = "https://api.themoviedb.org/3/";

    public static BaseApiServices getAPIService(){
        return RetrofitClient.getClient(BASE_URL_API).create(BaseApiServices.class);
    }
}
