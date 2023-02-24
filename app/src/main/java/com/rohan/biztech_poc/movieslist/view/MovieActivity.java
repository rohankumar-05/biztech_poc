package com.rohan.biztech_poc.movieslist.view;

import android.app.FragmentTransaction;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.util.List;

import com.rohan.biztech_poc.R;
import com.rohan.biztech_poc.movie_details.view.MovieSynopsisFragment;
import com.rohan.biztech_poc.movieslist.MovieContract;
import com.rohan.biztech_poc.movieslist.presenter.MovieListPresenter;
import com.rohan.biztech_poc.movieslist.view.adapter.MovieListAdapter;
import com.rohan.biztech_poc.movieslist.view.adapter.MoviesBaseItem;

public class MovieActivity extends AppCompatActivity implements MovieContract.View, MovieListAdapter.MovieActivityItemClickListener {

    private MovieContract.Presenter mPresenter;
    private MovieListAdapter mMovieListAdapter;
    private RecyclerView mRecyclerView;

    @Override
    public void onItemClick(MoviesBaseItem moviesBaseItem, MovieActivityItemClickType movieActivityItemClickType) {
        mPresenter.handleItemClick(moviesBaseItem, movieActivityItemClickType);
    }

    public enum MovieActivityItemClickType {
        VIEW_SYNOPSIS
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_activity);
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        mPresenter = new MovieListPresenter(this, connectivityManager);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        mRecyclerView = findViewById(R.id.movie_recyclerview);
        mMovieListAdapter = new MovieListAdapter(this);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setAdapter(mMovieListAdapter);
        mPresenter.getMoviesList();
    }

    @Override
    public void updateAdapterInformation(List<MoviesBaseItem> moviesBaseItemList) {
        if (mMovieListAdapter != null) {
            mMovieListAdapter.updateAdapterData(moviesBaseItemList);
        }
    }

    @Override
    public void displayMovieSynopsisFragment(String posterPath, Double popularity, String releaseDate, String overview, String original_language, String vote_average, String vote_count) {
        MovieSynopsisFragment movieSynopsisFragment = MovieSynopsisFragment.newInstance(posterPath, popularity, releaseDate, overview, original_language, vote_average, vote_count);
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.animator.enter, R.animator.exit)
                .replace(R.id.frame_container_layout, movieSynopsisFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public File getCacheDirectory() {
        return getCacheDir();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.popular:
                Toast.makeText(this, "Trending Movies", Toast.LENGTH_SHORT).show();
                mPresenter.getPopularMoviesList();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
