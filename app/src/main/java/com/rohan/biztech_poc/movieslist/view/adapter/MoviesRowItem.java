package com.rohan.biztech_poc.movieslist.view.adapter;

public class MoviesRowItem extends MoviesBaseItem {

    public final String mPosterPath;
    public final Double mPopularity;
    public final String mReleaseDate;
    public final String mOverView;
    public final String mOriginalLanguage;
    public final String mVoteAverage;
    public final String mVoteCount;

    public MoviesRowItem(String posterPath, Double popularity, String releaseDate, String overView, String originalLanguage, String voteAverage, String voteCount) {
        this.mPosterPath = posterPath;
        this.mPopularity = popularity;
        this.mReleaseDate = releaseDate;
        this.mOverView = overView;
        this.mOriginalLanguage = originalLanguage;
        this.mVoteAverage = voteAverage;
        this.mVoteCount = voteCount;
    }

    @Override
    public int getBaseItemType() {
        return MoviesBaseItem.ITEM_TYPE_MOVIE_ROW;
    }
}
