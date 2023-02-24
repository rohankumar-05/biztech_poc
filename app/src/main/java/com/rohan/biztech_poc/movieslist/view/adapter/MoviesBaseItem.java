package com.rohan.biztech_poc.movieslist.view.adapter;

public abstract class MoviesBaseItem {

    static final int ITEM_TYPE_MOVIE_HEADER = 1;
    static final int ITEM_TYPE_MOVIE_ROW = 2;

    public abstract int getBaseItemType();

}
