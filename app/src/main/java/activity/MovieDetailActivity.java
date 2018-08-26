package activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.shahzaib.movies.R;

import java.util.List;

import fragments.MovieDetailFragment;
import model.MovieVideo;
import model.MovieVideoResult;
import network.ApiClient;
import network.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailActivity extends AppCompatActivity{

    public static final String KEY_INTENT_MOVIE_ID = "movieID";

    View movie_trailer_container;
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    AppBarLayout appBarLayout;
    String movieID;








    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        toolbar = findViewById(R.id.toolbar);
        collapsingToolbarLayout = findViewById(R.id.movie_detail_collapsing_toolbar);
        movie_trailer_container = findViewById(R.id.movie_trailer_container);
        appBarLayout = findViewById(R.id.appBarLayout);



        // initial setup
        setupToolbar();
        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            boolean isShow = false;
            int scrollRange = -1;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.getTotalScrollRange();
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbarLayout.setTitle("Movie Detail");
                    isShow = true;
                } else if (isShow) {
                    collapsingToolbarLayout.setTitle(" "); //carefull there should a space between double quote otherwise it wont work
                    isShow = false;
                }
            }
        });
        movieID = getIntent().getStringExtra(KEY_INTENT_MOVIE_ID);
        MovieDetailFragment movieDetailFragment =((MovieDetailFragment) getSupportFragmentManager().findFragmentById(R.id.movie_detail_fragment)) ;
        movieDetailFragment.setMovieID(movieID);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }









    /** Helper methods***/
    private void setupToolbar() {
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        if(getSupportActionBar() !=null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    private void SHOW_LOG(String message) {
        Log.i("123456", message);
    }

}
