package fragments;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.shahzaib.movies.R;

import java.util.ArrayList;
import java.util.List;

import activity.MovieDetailActivity;
import activity.MovieReviewsActivity;
import model.Genres;
import model.MovieDetail;
import model.MovieVideo;
import model.MovieVideoResult;
import model.Review;
import model.ReviewContent;
import network.ApiClient;
import network.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailFragment extends Fragment implements View.OnClickListener {

    ImageView moviePoster, releaseStatusIV, movieTrailerThumbnail;
    TextView movieTitle, movieCategory, releaseStatusTV, movieOverview,ratingDetailTV, languageTV, releaseStatusTV2;
    RatingBar movieRatingBar,movieRatingBarInCircle;
    List<TextView> reviewAuthorTvList = new ArrayList<>();
    List<TextView> reviewsContentTvList = new ArrayList<>();
    View movieReviewsContainer , movieTrailerContainer;


    String movieID;
    ApiInterface apiInterface;
    String movieVideoKey;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movie_detail_fragment_layout, container, false);
        moviePoster = view.findViewById(R.id.movie_poster_IV);
        movieTitle = view.findViewById(R.id.movie_title);
        movieCategory = view.findViewById(R.id.movie_category);
        releaseStatusIV = view.findViewById(R.id.releaseStatusIV);
        releaseStatusTV = view.findViewById(R.id.releaseStatusTV);
        movieRatingBar = view.findViewById(R.id.movieRatingBar);
        movieOverview = view.findViewById(R.id.movie_overview_TV);
        movieTrailerThumbnail = view.findViewById(R.id.movie_trailer_thumbnail);
        ratingDetailTV = view.findViewById(R.id.ratingDetailTV);
        movieRatingBarInCircle = view.findViewById(R.id.movie_rating_bar_in_circle);
        languageTV = view.findViewById(R.id.languageTV);
        releaseStatusTV2 = view.findViewById(R.id.releaseStatusTV2);
        movieTrailerContainer = view.findViewById(R.id.movie_trailer_container);
        reviewAuthorTvList.add((TextView) view.findViewById(R.id.reviewAuthor1));
        reviewAuthorTvList.add((TextView) view.findViewById(R.id.reviewAuthor2));
        reviewAuthorTvList.add((TextView) view.findViewById(R.id.reviewAuthor3));
        reviewsContentTvList.add((TextView) view.findViewById(R.id.reviewContent1));
        reviewsContentTvList.add((TextView) view.findViewById(R.id.reviewContent2));
        reviewsContentTvList.add((TextView) view.findViewById(R.id.reviewContent3));
        movieReviewsContainer = view.findViewById(R.id.movie_review_container);
        apiInterface = ApiClient.getClient().create(ApiInterface.class);

        // initial setup
        movieReviewsContainer.setOnClickListener(this);
        movieTrailerContainer.setOnClickListener(this);
        makeInvisibleAllReviews();


        return view;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.movie_review_container:
                Toast.makeText(getContext(), "Review clicked", Toast.LENGTH_SHORT).show();
                Intent movieReviewsActivity = new Intent(getContext(), MovieReviewsActivity.class);
                SHOW_LOG("Movie ID passed to Reviews activity: " + movieID);
                movieReviewsActivity.putExtra(MovieReviewsActivity.KEY_INTENT_MOVIE_ID, movieID);
                startActivity(movieReviewsActivity);
                break;

            case R.id.movie_trailer_container:
                Toast.makeText(getContext(), "Play Trailer", Toast.LENGTH_SHORT).show();
                playMovieVideo(movieVideoKey);
                break;
        }
    }


    /* Helper Methods */
    private void SHOW_LOG(String message) {
        Log.i("123456", message);
    }

    public void setMovieID(String movieID) {
        this.movieID = movieID;
        //get movie data & bind with ui
        apiInterface.getMovieDetail(movieID).enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Call<MovieDetail> call, Response<MovieDetail> response) {
                SHOW_LOG("Successfully fetched movie detail");
                MovieDetail movieDetail = response.body();
                bindDetailedDataToUI(movieDetail);
            }

            @Override
            public void onFailure(Call<MovieDetail> call, Throwable t) {
                SHOW_LOG("Failed to fetch movie detail");
                SHOW_LOG("Exception: " + t.toString());
            }
        });
        apiInterface.getReviews(movieID).enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                if (response.body() == null) {
                    SHOW_LOG("Fetched review response is null");
                    return;
                }
                bindReviewsToUI(response.body().getReviewContentList());
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                SHOW_LOG("Fetching review data failed: " + t.toString());
            }
        });
        apiInterface.getVideoDetail(movieID).enqueue(new Callback<MovieVideo>() {
            @Override
            public void onResponse(Call<MovieVideo> call, Response<MovieVideo> response) {
                setMovieVideoKey(response.body().getMovieVideoResultList());
            }

            @Override
            public void onFailure(Call<MovieVideo> call, Throwable t) {
                SHOW_LOG("Getting movie video detail failed: "+t.toString());
            }
        });

    }

    private void bindDetailedDataToUI(MovieDetail movieDetail) {
        movieTitle.setText(movieDetail.getTitle());
        Glide.with(getContext()).load(movieDetail.getPosterPath()).into(moviePoster);
        Glide.with(getContext()).load(movieDetail.getBackdropPath()).into(movieTrailerThumbnail);
        releaseStatusTV.setText(movieDetail.getStatus());
        movieRatingBar.setRating(movieDetail.getVoteAverage() - 5f );
        movieRatingBarInCircle.setRating(movieDetail.getVoteAverage() - 5f);
        movieOverview.setText(movieDetail.getOverview());
        movieCategory.setText(getMovieCategoryString(movieDetail.getGenresList()));
        releaseStatusTV.setText(movieDetail.getStatus());
        releaseStatusTV2.setText(movieDetail.getStatus());
        ratingDetailTV.setText(String.valueOf(movieDetail.getVoteAverage()));
        languageTV.setText(movieDetail.getOrignalLanguage());

    }

    private void bindReviewsToUI(List<ReviewContent> reviewContentList) {
        if (reviewContentList.size() == 0) return;
        else if (reviewContentList.size() <= 3) {
            for (int i = 0; i < reviewContentList.size(); i++) {
                reviewAuthorTvList.get(i).setText(reviewContentList.get(i).getReviewAuthorName());
                reviewsContentTvList.get(i).setText(reviewContentList.get(i).getReviewContent());
                reviewAuthorTvList.get(i).setVisibility(View.VISIBLE);
                reviewsContentTvList.get(i).setVisibility(View.VISIBLE);
            }
        } else {
            for (int i = 0; i < 3; i++) {
                reviewAuthorTvList.get(i).setText(reviewContentList.get(i).getReviewAuthorName());
                reviewsContentTvList.get(i).setText(reviewContentList.get(i).getReviewContent());
                reviewAuthorTvList.get(i).setVisibility(View.VISIBLE);
                reviewsContentTvList.get(i).setVisibility(View.VISIBLE);
            }
        }
        movieReviewsContainer.setVisibility(View.VISIBLE);
    }

    private String getMovieCategoryString(List<Genres> genresList) {
        if (genresList != null) {
            StringBuilder categoryString = new StringBuilder();
            for (int i = 0; i < genresList.size(); i++) {
                categoryString.append(genresList.get(i).getName() + ", ");
            }
            return categoryString.toString();
        }
        return "";
    }

    private void makeInvisibleAllReviews() {
        movieReviewsContainer.setVisibility(View.GONE);
        for (int i = 0; i < reviewAuthorTvList.size(); i++) {
            reviewAuthorTvList.get(i).setVisibility(View.GONE);
            reviewsContentTvList.get(i).setVisibility(View.GONE);
        }
    }

    public void setMovieVideoKey(List<MovieVideoResult> movieVideoKeyList) {
        if(movieVideoKeyList != null && movieVideoKeyList.size()>0)
        {
            this.movieVideoKey = movieVideoKeyList.get(0).getKey();
            SHOW_LOG("Movie video key is set.....");
        }
    }

    private void playMovieVideo(String movieVideoKey){
        SHOW_LOG("Movie Video Key: "+movieVideoKey);
        Intent youtubeAppIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + movieVideoKey));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.youtube.com/watch?v=" + movieVideoKey));
        try {
            startActivity(youtubeAppIntent);
        } catch (ActivityNotFoundException ex) {
            startActivity(webIntent);
        }
    }
}
