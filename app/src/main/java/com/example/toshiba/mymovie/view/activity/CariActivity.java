package com.example.toshiba.mymovie.view.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.widget.Toast;

import com.example.toshiba.mymovie.R;
import com.example.toshiba.mymovie.adapter.AMovie;
import com.example.toshiba.mymovie.model.ResponseMovie;
import com.example.toshiba.mymovie.model.ResultsItem;
import com.example.toshiba.mymovie.retrofit.BaseApiServices;
import com.example.toshiba.mymovie.retrofit.UtilsApi;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CariActivity extends AppCompatActivity {

    private List<ResultsItem> movieList = new ArrayList<>();
    private RecyclerView rvMoview;
    private SearchView searchView;
    private AMovie aMovie;
    private BaseApiServices baseApiServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cari_activity);

        initView();
        initEvent();
        getSearchMoview("america");
    }

    private void initEvent() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                getSearchMoview(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getSearchMoview(newText);
                return false;
            }
        });
    }

    private void getSearchMoview(String query) {
        Locale locale = getResources().getConfiguration().locale;
        String language =  locale.getLanguage();
        if (locale.toString().equalsIgnoreCase("in_id")){
             language = "id";
        }
        baseApiServices = UtilsApi.getAPIService();
        baseApiServices.getMovieSearch(""+query, language)
                .enqueue(new Callback<ResponseMovie>() {
                    @Override
                    public void onResponse(Call<ResponseMovie> call, Response<ResponseMovie> response) {
                        if (response.isSuccessful()) {
                            movieList.clear();
                            movieList.addAll(response.body().getResults());
                            aMovie.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getBaseContext(), getString(R.string.not_found), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseMovie> call, Throwable t) {
                        Toast.makeText(getBaseContext(), getString(R.string.failure), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void initView() {
        searchView = findViewById(R.id.svMoview);
        rvMoview = findViewById(R.id.rvMoview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvMoview.setLayoutManager(linearLayoutManager);
        rvMoview.setNestedScrollingEnabled(false);
        aMovie = new AMovie(this, movieList);
        rvMoview.setAdapter(aMovie);
    }
}
