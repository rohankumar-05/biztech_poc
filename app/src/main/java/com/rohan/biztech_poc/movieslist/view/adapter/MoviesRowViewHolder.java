package com.rohan.biztech_poc.movieslist.view.adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.rohan.biztech_poc.R;

public class MoviesRowViewHolder extends MoviesBaseViewHolder {

    public CardView mMovieRowCardView;
    public ImageView mMovieThumbNailImageView;
    public TextView mPopularityTextView;
    public TextView mReleaseDateTextView;

    public MoviesRowViewHolder(View itemView) {
        super(itemView);
        mMovieRowCardView = itemView.findViewById(R.id.movie_row_cardview);
        mMovieThumbNailImageView = itemView.findViewById(R.id.movies_thumbnail_image_view);
        mPopularityTextView = itemView.findViewById(R.id.movie_popularity_text_view);
        mReleaseDateTextView = itemView.findViewById(R.id.movie_release_date_text_view);
    }

}
