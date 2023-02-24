package com.rohan.biztech_poc.movie_details;

public class MovieSynopsisContract {

    public interface View {
        void showMovieDetails(String mPosterPath, Double mPopularity, String mReleaseDate, String mOverview, String originalLanguage, String averageVote, String voteCount);
    }

    public interface Presenter {
        void showMovieSynopsis();
    }

}



