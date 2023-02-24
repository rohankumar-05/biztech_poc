package com.rohan.biztech_poc.network.service.movies;

import com.rohan.biztech_poc.network.service.movies.response.Movie;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MoviesService {

    @GET("movie/now_playing")
    Call<Movie> getMoviesList(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<Movie> getPopularMoviesList(@Query("api_key") String apiKey);

}
