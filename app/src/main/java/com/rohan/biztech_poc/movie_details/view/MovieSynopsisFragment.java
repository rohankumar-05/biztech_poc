package com.rohan.biztech_poc.movie_details.view;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import com.rohan.biztech_poc.R;
import com.rohan.biztech_poc.movie_details.MovieSynopsisContract;
import com.rohan.biztech_poc.movie_details.presenter.MovieSynopsisPresenter;

public class MovieSynopsisFragment extends Fragment implements MovieSynopsisContract.View{

    private static final String EXTRA_POSTER_PATH = "extra_poster_path";
    private static final String EXTRA_POPULARITY = "extra_popularity";
    private static final String EXTRA_RELEASE_DATE = "extra_release_date";

    private static final String MOVIE_OVERVIEW = "movie_overview";

    private static final String MOVIE_ORIGINAL_LANGUAGE = "movie_language";

    private static final String MOVIE_AVERAGE_VOTE = "movie_average_vote";

    private static final String MOVIE_VOTE_COUNT = "movie_vote_count";

    private MovieSynopsisContract.Presenter mPresenter;

    private ImageView mMovieThumbNailImageView;
    private TextView mPopulaityTextView;
    private TextView mReleaseDateTextView;
    private TextView mMovieOverview;
    private TextView mMovieOriginalLanguage;
    private TextView mMovieVoteAverage;
    private TextView mMovieVoteCount;

    public static MovieSynopsisFragment newInstance(String posterPath, Double popularity, String releaseDate, String overview, String org_language, String avg_vote, String vote_count) {
        MovieSynopsisFragment fragment = new MovieSynopsisFragment();
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_POSTER_PATH, posterPath);
        bundle.putDouble(EXTRA_POPULARITY, popularity);
        bundle.putString(EXTRA_RELEASE_DATE, releaseDate);
        bundle.putString(MOVIE_OVERVIEW, overview);
        bundle.putString(MOVIE_ORIGINAL_LANGUAGE, org_language);
        bundle.putString(MOVIE_AVERAGE_VOTE, avg_vote);
        bundle.putString(MOVIE_VOTE_COUNT, vote_count);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            String posterPath = getArguments().getString(EXTRA_POSTER_PATH);
            Double popularity = getArguments().getDouble(EXTRA_POPULARITY);
            String releaseDate = getArguments().getString(EXTRA_RELEASE_DATE);
            String overview = getArguments().getString(MOVIE_OVERVIEW);
            String originalLanguage = getArguments().getString(MOVIE_ORIGINAL_LANGUAGE);
            String averageVote = getArguments().getString(MOVIE_AVERAGE_VOTE);
            String voteCount = getArguments().getString(MOVIE_VOTE_COUNT);
            mPresenter = new MovieSynopsisPresenter(this, posterPath, popularity, releaseDate, overview, originalLanguage, averageVote, voteCount);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_synopsis, container, false);
        mMovieThumbNailImageView = view.findViewById(R.id.movies_thumbnail_fragment_image_view);
        mPopulaityTextView = view.findViewById(R.id.movie_popularity_fragment_text_view);
        mReleaseDateTextView = view.findViewById(R.id.movie_release_date_fragment_text_view);
        mMovieOverview = view.findViewById(R.id.movie_overview);
        mMovieOriginalLanguage = view.findViewById(R.id.movie_language);
        mMovieVoteAverage = view.findViewById(R.id.movie_avg_vote);
        mMovieVoteCount = view.findViewById(R.id.movie_vote_count);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPresenter.showMovieSynopsis();
    }

    @Override
    public void showMovieDetails(String posterPath, Double popularity, String releaseDate, String overview, String originalLanguage, String averageVote, String voteCount) {
        Glide.with(mMovieThumbNailImageView.getContext())
                .load("https://image.tmdb.org/t/p/w500/" + posterPath)
                .centerCrop()
                .into(mMovieThumbNailImageView);
        mPopulaityTextView.setText(String.valueOf(popularity));
        mReleaseDateTextView.setText(releaseDate);
        mMovieOverview.setText(overview);
        mMovieOriginalLanguage.setText(originalLanguage);
        mMovieVoteAverage.setText(averageVote);
        mMovieVoteCount.setText(voteCount);
    }
}
