package fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.shahzaib.movies.R;

import java.util.List;

import activity.MainActivity;
import activity.MovieDetailActivity;
import adapter.MoviesAdapter;
import model.Movie;
import model.Result;
import network.ApiClient;
import network.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesListFragment extends Fragment implements SharedPreferences.OnSharedPreferenceChangeListener,
        MoviesAdapter.OnItemClickListener, Callback<Movie> {

    public static int NUMBER_OF_COLUMNS = 2;
    MoviesAdapter adapter;
    RecyclerView recyclerView;
    ApiInterface apiInterface;
    ProgressBar movies_list_progress_circle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.movies_list_fragment, container, false);
        // initialization
        recyclerView = view.findViewById(R.id.movies_list_recyclerView);
        movies_list_progress_circle = view.findViewById(R.id.movies_list_progress_circle);
        adapter = new MoviesAdapter();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);




        //initial Setup

        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // In landscape
            NUMBER_OF_COLUMNS = 3;
        } else {
            // In portrait
            NUMBER_OF_COLUMNS = 2;
        }
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), NUMBER_OF_COLUMNS));
        adapter.setListener(this);
        PreferenceManager.getDefaultSharedPreferences(getContext()).registerOnSharedPreferenceChangeListener(this);
        setMoviesDefaultList(PreferenceManager.getDefaultSharedPreferences(getContext()).getString(getString(R.string.default_sort), null));
        recyclerView.setVisibility(View.GONE);
        movies_list_progress_circle.setVisibility(View.VISIBLE);

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        boolean shouldShowMovieInfo = sharedPreferences.getBoolean(getString(R.string.show_movie_info), true);
        setRecyclerViewShowMovieInfo(shouldShowMovieInfo);

    }



    @Override
    public void onResponse(Call<Movie> call, Response<Movie> response) {
        SHOW_LOG("Response Successfull...");
        setRecyclerViewData(response.body().getResults());
        recyclerView.setVisibility(View.VISIBLE);
        movies_list_progress_circle.setVisibility(View.GONE);
    }

    @Override
    public void onFailure(Call<Movie> call, Throwable t) {
        SHOW_LOG("Response Failed...");
    }










    /**
     *  Listeners
     */
    @Override
    public void onMovieItemClick(String movieID) {
        SHOW_LOG("clicked item MovieID: " + movieID);
        Intent movieDetailActivityIntent = new Intent(getActivity(), MovieDetailActivity.class);
        movieDetailActivityIntent.putExtra(MovieDetailActivity.KEY_INTENT_MOVIE_ID, movieID);
        startActivity(movieDetailActivityIntent);

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.show_movie_info))) {

            setRecyclerViewShowMovieInfo(sharedPreferences.getBoolean(getString(R.string.show_movie_info), true));

        } else if (key.equals(getString(R.string.default_sort)))
        {
            String value = sharedPreferences.getString(key, null);
            if (value != null) {
                if (value.equals(getString(R.string.value_popular_movies))) {
                    Toast.makeText(getContext(), "Set Popular movies as default", Toast.LENGTH_SHORT).show();
                } else if (value.equals(getString(R.string.value_now_playing_movies))) {
                    Toast.makeText(getContext(), "Set Now playing movies as default", Toast.LENGTH_SHORT).show();
                } else if (value.equals(getString(R.string.value_upcoming_movies))) {
                    Toast.makeText(getContext(), "Set upcoming movies as default", Toast.LENGTH_SHORT).show();
                } else if (value.equals(getString(R.string.value_favourite_movies))) {
                    Toast.makeText(getContext(), "Set Favourite movies collection as default", Toast.LENGTH_SHORT).show();
                }
            }
        }

    }








    /**
     * Helper Methods
     */
    private void SHOW_LOG(String message) {
        Log.i("123456", message);
    }

    private void setMoviesDefaultList(String defaultValue) {
        if (defaultValue != null) {
            if (defaultValue.equals(getString(R.string.value_popular_movies))) {
                showPopularMovies();

            } else if (defaultValue.equals(getString(R.string.value_now_playing_movies))) {
                showNowPlayingMovies();




            } else if (defaultValue.equals(getString(R.string.value_upcoming_movies))) {
                showUpcomingMovies();


            } else if (defaultValue.equals(getString(R.string.value_favourite_movies))) {
                Toast.makeText(getContext(), "Showing favourite movies", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(getContext(), "Default Sort value is Null", Toast.LENGTH_SHORT).show();
            showPopularMovies();
        }
    }
    private void setRecyclerViewShowMovieInfo(boolean visibility) {
        adapter.setMovieInfoVisibility(visibility);
        recyclerView.setAdapter(adapter);
    }
    private void setRecyclerViewData(List<Result> moviesList) {
        adapter.setData(moviesList);
        recyclerView.setAdapter(adapter);
    }
    public void showNowPlayingMovies() {
        apiInterface.getNowPlayingMovies().enqueue(this);
    }
    public void showPopularMovies() {
        apiInterface.getPopularMovies().enqueue(this);
    }
    public void showUpcomingMovies() {
        apiInterface.getUpcomingMovies().enqueue(this);
    }

}
