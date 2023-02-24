package com.rohan.biztech_poc.movieslist.view.adapter;

public class MoviesHeaderItem extends MoviesBaseItem {

    public final String mMovieTitle;

    public MoviesHeaderItem(String movieName) {
        this.mMovieTitle = movieName;
    }

    @Override
    public int getBaseItemType() {
        return MoviesBaseItem.ITEM_TYPE_MOVIE_HEADER;
    }
}
