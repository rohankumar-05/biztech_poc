package com.rohan.biztech_poc.movieslist;

import java.io.File;
import java.util.List;

import com.rohan.biztech_poc.movieslist.view.MovieActivity;
import com.rohan.biztech_poc.movieslist.view.adapter.MoviesBaseItem;
import com.rohan.biztech_poc.network.service.movies.response.Result;

public class MovieContract {

    public interface View {

        void updateAdapterInformation(List<MoviesBaseItem> moviesBaseItemList);

        void displayMovieSynopsisFragment(String mPosterPath, Double mPopularity, String mReleaseDate, String mOverview, String mOriginalLanguage, String mVoteAverage, String mVoteCount);

        File getCacheDirectory();
    }

    public interface Presenter {

        void getMoviesList();

        void handleNetworkResponse(List<Result> moviesList);

        void handleItemClick(MoviesBaseItem moviesBaseItem, MovieActivity.MovieActivityItemClickType movieActivityItemClickType);

        void getPopularMoviesList();

    }

}
