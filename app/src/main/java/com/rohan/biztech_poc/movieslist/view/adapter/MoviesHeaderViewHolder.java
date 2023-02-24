package com.rohan.biztech_poc.movieslist.view.adapter;

import android.view.View;
import android.widget.TextView;

import com.rohan.biztech_poc.R;

public class MoviesHeaderViewHolder extends MoviesBaseViewHolder {

    public TextView mMovieTitleTextView;

    public MoviesHeaderViewHolder(View itemView) {
        super(itemView);
        mMovieTitleTextView = itemView.findViewById(R.id.movie_title_text);
    }
}
