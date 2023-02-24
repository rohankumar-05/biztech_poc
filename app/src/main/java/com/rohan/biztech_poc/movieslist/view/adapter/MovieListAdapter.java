package com.rohan.biztech_poc.movieslist.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.rohan.biztech_poc.R;
import com.rohan.biztech_poc.movieslist.view.MovieActivity.MovieActivityItemClickType;

import java.util.List;

public class MovieListAdapter extends RecyclerView.Adapter<MoviesBaseViewHolder> implements View.OnClickListener {

    private List<MoviesBaseItem> mMoviesBaseItemList;
    Context mContext;

    private MovieActivityItemClickListener mItemClickListener;

    public MovieListAdapter(MovieActivityItemClickListener movieActivityItemClickListener) {
        mItemClickListener = movieActivityItemClickListener;
    }

    public interface MovieActivityItemClickListener {
        void onItemClick(MoviesBaseItem moviesBaseItem, MovieActivityItemClickType movieActivityItemClickType);
    }

    @Override
    public MoviesBaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        MoviesBaseViewHolder viewHolder = null;
        switch (viewType) {
            case MoviesHeaderItem.ITEM_TYPE_MOVIE_HEADER:
                View headerItem = layoutInflater.inflate(R.layout.movies_header_item, parent, false);
                viewHolder = new MoviesHeaderViewHolder(headerItem);
                break;
            case MoviesRowItem.ITEM_TYPE_MOVIE_ROW:
                View rowItem = layoutInflater.inflate(R.layout.movies_row_item, parent, false);
                viewHolder = new MoviesRowViewHolder(rowItem);
                break;
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MoviesBaseViewHolder viewHolder, int position) {
        if (viewHolder == null) {
            return;
        }

        MoviesBaseItem moviesBaseItem = mMoviesBaseItemList.get(position);
        if (moviesBaseItem != null) {
            if (viewHolder instanceof MoviesHeaderViewHolder) {
                MoviesHeaderViewHolder moviesHeaderViewHolder = (MoviesHeaderViewHolder) viewHolder;
                MoviesHeaderItem moviesHeaderItem = (MoviesHeaderItem) moviesBaseItem;
                moviesHeaderViewHolder.mMovieTitleTextView.setText(moviesHeaderItem.mMovieTitle);

            } else if (viewHolder instanceof MoviesRowViewHolder) {
                MoviesRowViewHolder moviesRowViewHolder = (MoviesRowViewHolder) viewHolder;
                MoviesRowItem moviesRowItem = (MoviesRowItem) moviesBaseItem;
                Glide.with(moviesRowViewHolder.mMovieThumbNailImageView.getContext())
                        .load("https://image.tmdb.org/t/p/w500/" + moviesRowItem.mPosterPath)
                        .centerCrop()
                        .into(moviesRowViewHolder.mMovieThumbNailImageView);
                moviesRowViewHolder.mPopularityTextView.setText(String.valueOf(moviesRowItem.mPopularity));
                moviesRowViewHolder.mReleaseDateTextView.setText(moviesRowItem.mReleaseDate);

                moviesRowViewHolder.mMovieRowCardView.setTag(R.id.key_item, moviesRowItem);
                moviesRowViewHolder.mMovieRowCardView.setTag(R.id.key_item_click_type, MovieActivityItemClickType.VIEW_SYNOPSIS);
                moviesRowViewHolder.mMovieRowCardView.setOnClickListener(this);

//                if (isLastElementInRow(position)) {
//
//                    new Handler().postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            makeInfiniteScroll(mMoviesBaseItemList);
//                        }
//                    }, 1500);
//
//                }
            }
        }
    }

    private void makeInfiniteScroll(List<MoviesBaseItem> moviesBaseItemList) {
        mMoviesBaseItemList.addAll(moviesBaseItemList);
        notifyDataSetChanged();
    }


    @Override
    public void onClick(View v) {
        MoviesBaseItem moviesBaseItem = null;
        MovieActivityItemClickType movieActivityItemClickType = null;

        Object itemObject = v.getTag(R.id.key_item);
        if (itemObject instanceof MoviesBaseItem) {
            moviesBaseItem = (MoviesBaseItem) itemObject;
        }

        Object clickTypeObject = v.getTag(R.id.key_item_click_type);
        if (clickTypeObject instanceof MovieActivityItemClickType) {
            movieActivityItemClickType = (MovieActivityItemClickType) clickTypeObject;
        }

        if (clickTypeObject != null) {
            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(moviesBaseItem, movieActivityItemClickType);
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mMoviesBaseItemList != null) {
            return mMoviesBaseItemList.size();
        }
        return 0;
    }

    @Override
    public int getItemViewType(int position) {
        Integer itemType = getItemType(position);
        if (itemType != null) {
            return itemType;
        }
        return super.getItemViewType(position);
    }

    private Integer getItemType(int position) {
        MoviesBaseItem moviesBaseItem = getItemByPosition(position);
        if (moviesBaseItem != null) {
            return moviesBaseItem.getBaseItemType();
        }

        return null;
    }

    private MoviesBaseItem getItemByPosition(int position) {
        if (mMoviesBaseItemList != null && !mMoviesBaseItemList.isEmpty()) {
            if (position >= 0 && position < mMoviesBaseItemList.size()) {
                return mMoviesBaseItemList.get(position);
            }
        }
        return null;
    }

    public void updateAdapterData(List<MoviesBaseItem> moviesBaseItemList) {
        mMoviesBaseItemList = moviesBaseItemList;
        notifyDataSetChanged();
    }

    private boolean isLastElementInRow(int position) {
        if (mMoviesBaseItemList.get(position) instanceof MoviesRowItem) {
            //checking whether last element of list
            if (position + 1 == getItemCount()) {
                return true;
            }
        }
        return false;
    }

}
