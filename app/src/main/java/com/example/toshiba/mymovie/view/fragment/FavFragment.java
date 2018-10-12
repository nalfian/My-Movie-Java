package com.example.toshiba.mymovie.view.fragment;


import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toshiba.mymovie.R;
import com.example.toshiba.mymovie.adapter.AMovie;
import com.example.toshiba.mymovie.model.ResultsItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.example.toshiba.mymovie.db.DatabaseContract.MovieColumns.CONTENT_URI;
import static com.example.toshiba.mymovie.db.DatabaseContract.MovieColumns.ID_MOVIE;
import static com.example.toshiba.mymovie.db.DatabaseContract.MovieColumns.OVERVIEW;
import static com.example.toshiba.mymovie.db.DatabaseContract.MovieColumns.POSTER_PATH;
import static com.example.toshiba.mymovie.db.DatabaseContract.MovieColumns.REALESE_DATE;
import static com.example.toshiba.mymovie.db.DatabaseContract.MovieColumns.TITLE;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavFragment extends Fragment {

    private List<ResultsItem> movieList = new ArrayList<>();
    private RecyclerView rvMoview;
    private AMovie aMovie;
    public static AsyncTask<Void, Void, Cursor> asyncTask;

    public FavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_fav, container, false);
        initView(view);
        getMovie();
        return view;
    }

    @SuppressLint("StaticFieldLeak")
    private void getMovie() {
        asyncTask = new AsyncTask<Void, Void, Cursor>() {
            @Override
            protected Cursor doInBackground(Void... voids) {
                movieList.clear();
                return getContext().getContentResolver().query(CONTENT_URI,
                        null,
                        null,
                        null,
                        null);
            }

            @Override
            protected void onPostExecute(Cursor cursor) {
                super.onPostExecute(cursor);
                cursor.moveToFirst();
                ResultsItem fav;
                if (cursor.getCount() > 0) {
                    do {
                        fav = new ResultsItem();
                        fav.setId(cursor.getInt(cursor.getColumnIndexOrThrow(ID_MOVIE)));
                        fav.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                        fav.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(OVERVIEW)));
                        fav.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));
                        fav.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(REALESE_DATE)));
                        fav.setPosterPath(cursor.getString(cursor.getColumnIndexOrThrow(POSTER_PATH)));

                        movieList.add(fav);
                        cursor.moveToNext();

                    } while (!cursor.isAfterLast());
                }
                cursor.close();
                aMovie.notifyDataSetChanged();
            }
        };

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
