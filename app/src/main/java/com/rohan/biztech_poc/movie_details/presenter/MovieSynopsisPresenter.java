package com.rohan.biztech_poc.movie_details.presenter;


import androidx.annotation.NonNull;

import com.rohan.biztech_poc.movie_details.MovieSynopsisContract;

public class MovieSynopsisPresenter implements MovieSynopsisContract.Presenter{

    private final MovieSynopsisContract.View mView;
    private String mPosterPath;
    private Double mPopularity;
    private String mReleaseDate;
    private String mOverview;
    private String mOriginalLanguage;
    private String mVoteAverage;
    private String mVoteCount;

    public MovieSynopsisPresenter(@NonNull MovieSynopsisContract.View view, String posterPath, Double popularity, String releaseDate, String overview, String originalLanguage, String averageVote, String voteCount) {
        mView = view;
        mPosterPath = posterPath;
        mPopularity = popularity;
        mReleaseDate = releaseDate;
        mOverview = overview;
        mOriginalLanguage = originalLanguage;
        mVoteAverage = averageVote;
        mVoteCount = voteCount;
    }

    @Override
    public void showMovieSynopsis() {
        mView.showMovieDetails(mPosterPath, mPopularity, mReleaseDate, mOverview, mOriginalLanguage, mVoteAverage, mVoteCount);
    }
}
