package com.rohan.biztech_poc.movieslist.presenter;

import android.net.ConnectivityManager;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.rohan.biztech_poc.core.AppConstants;
import com.rohan.biztech_poc.movieslist.MovieContract;
import com.rohan.biztech_poc.movieslist.view.MovieActivity;
import com.rohan.biztech_poc.movieslist.view.adapter.MoviesBaseItem;
import com.rohan.biztech_poc.movieslist.view.adapter.MoviesHeaderItem;
import com.rohan.biztech_poc.movieslist.view.adapter.MoviesRowItem;
import com.rohan.biztech_poc.network.NetworkUtils;
import com.rohan.biztech_poc.network.service.movies.MoviesService;
import com.rohan.biztech_poc.network.service.movies.response.Movie;
import com.rohan.biztech_poc.network.service.movies.response.Result;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MovieListPresenter implements MovieContract.Presenter, Callback<Movie> {

    // Logging Tag
    private static final String TAG = MovieListPresenter.class.getSimpleName();

    private final MovieContract.View mView;
    private ConnectivityManager mConnectivityManager;

    public MovieListPresenter(@NonNull MovieContract.View view, ConnectivityManager connectivityManager) {
        mView = view;
        mConnectivityManager = connectivityManager;
    }

    List<Result> mResultsList = new ArrayList<>();

    @Override
    public void getMoviesList() {
        int cacheSize = 10 * 1024 * 1024; // 10 MB
        File file = mView.getCacheDirectory();
        File httpCacheDirectory = new File(file, "httpCache");
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        if (!NetworkUtils.isOnline(mConnectivityManager)) {
                            int maxStale = 60 * 30;
                            request = request.newBuilder().header("Cache-Control", "public, only-if-cached," +
                                    "max-stale=" + maxStale).build();
                        }
                        return chain.proceed(request);
                    }
                }).build();

        Gson gson = new GsonBuilder()
                .setDateFormat(AppConstants.DATE_FORMAT)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.API_BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        MoviesService service = retrofit.create(MoviesService.class);
        Call<Movie> call = service.getMoviesList("7afe54fac01b7e8e6649018b3c30cc56");
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<Movie> call, Response<Movie> response) {
        if (response.isSuccessful()) {
            handleNetworkResponse(response.body().getResults());
        } else {
            Log.e(TAG, "makeNetworkRequest: error getting movie details- ");
        }
    }

    @Override
    public void onFailure(Call<Movie> call, Throwable t) {
        Log.e(TAG, "makeNetworkRequest: error getting movie details- ");
    }

    public void handleNetworkResponse(List<Result> resultsList) {
        mResultsList = resultsList;
        populateMoviesBaseItemList(resultsList);
    }

    void populateMoviesBaseItemList(List<Result> resultsList) {
        List<MoviesBaseItem> moviesBaseItemList = new ArrayList<>();
        for (Result result : resultsList) {
            moviesBaseItemList.add(new MoviesHeaderItem(result.getTitle()));
            moviesBaseItemList.add(new MoviesRowItem(result.getPoster_path(), result.getPopularity(), result.getRelease_date(), result.getOverview(), result.getOriginal_language(), result.getVote_average(), result.getVote_count()));
        }
        mView.updateAdapterInformation(moviesBaseItemList);
    }

    @Override
    public void handleItemClick(MoviesBaseItem moviesBaseItem, MovieActivity.MovieActivityItemClickType movieActivityItemClickType) {
        if (movieActivityItemClickType != null) {
            switch (movieActivityItemClickType) {
                case VIEW_SYNOPSIS:
                    MoviesRowItem moviesRowItem = (MoviesRowItem) moviesBaseItem;
                    mView.displayMovieSynopsisFragment(moviesRowItem.mPosterPath, moviesRowItem.mPopularity, moviesRowItem.mReleaseDate, moviesRowItem.mOverView, moviesRowItem.mOriginalLanguage, moviesRowItem.mVoteAverage, moviesRowItem.mVoteCount);
                    break;
            }
        }
    }

    @Override
    public void getPopularMoviesList() {
        int cacheSize = 10 * 1024 * 1024; // 10 MB
        File file = mView.getCacheDirectory();
        File httpCacheDirectory = new File(file, "httpCache");
        Cache cache = new Cache(httpCacheDirectory, cacheSize);
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .cache(cache)
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        Request request = chain.request();
                        if (!NetworkUtils.isOnline(mConnectivityManager)) {
                            int maxStale = 60 * 30;
                            request = request.newBuilder().header("Cache-Control", "public, only-if-cached," +
                                    "max-stale=" + maxStale).build();
                        }
                        return chain.proceed(request);
                    }
                }).build();

        Gson gson = new GsonBuilder()
                .setDateFormat(AppConstants.DATE_FORMAT)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(AppConstants.API_BASE_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        MoviesService service = retrofit.create(MoviesService.class);
        Call<Movie> call = service.getPopularMoviesList("7afe54fac01b7e8e6649018b3c30cc56");
        call.enqueue(this);
    }

}
