package com.example.toshiba.mymovie.view.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

/**
 * A simple {@link Fragment} subclass.
 */
public class UpComingFragment extends Fragment {

    private List<ResultsItem> movieList = new ArrayList<>();
    private RecyclerView rvMoview;
    private AMovie aMovie;
    private BaseApiServices baseApiServices;

    public UpComingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_up_coming, container, false);
        initView(view);
        getMovie();
        return view;
    }

    private void getMovie() {
        Locale locale = getResources().getConfiguration().locale;
        String language =  locale.getLanguage();
        if (locale.toString().equalsIgnoreCase("in_id")){
            language = "id";
        }
        baseApiServices = UtilsApi.getAPIService();
        baseApiServices.getMovieUpcoming(language)
                .enqueue(new Callback<ResponseMovie>() {
                    @Override
                    public void onResponse(Call<ResponseMovie> call, Response<ResponseMovie> response) {
                        if (response.isSuccessful()) {
                            movieList.clear();
                            movieList.addAll(response.body().getResults());
                            aMovie.notifyDataSetChanged();
                        } else {
                            Toast.makeText(getContext(), getString(R.string.not_found), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseMovie> call, Throwable t) {
                        if (getContext()!=null) {
                            Toast.makeText(getContext(), getString(R.string.failure), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void initView(View view) {
        rvMoview = view.findViewById(R.id.rvMoview);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        rvMoview.setLayoutManager(linearLayoutManager);
        rvMoview.setNestedScrollingEnabled(false);
        aMovie = new AMovie(getContext(), movieList);
        rvMoview.setAdapter(aMovie);
    }

}
