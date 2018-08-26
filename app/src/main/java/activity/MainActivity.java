package activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.shahzaib.movies.R;

import fragments.MoviesListFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    MoviesListFragment moviesListFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_drawer_layout);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        toolbar = findViewById(R.id.toolbar);
        moviesListFragment = (MoviesListFragment) getSupportFragmentManager().findFragmentById(R.id.movies_list_fragment);


        // initial Setup
        setupToolbar();
        setupNavigationDrawer();
        setDefaultMoviesListToolbarTitle();


    }

    @Override
    public void onBackPressed() {
        if (isDrawerOpen()) closeDrawer();
        else super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case R.id.nowPlayingMovies:
                moviesListFragment.showNowPlayingMovies();
                setToolbarTitle(getString(R.string.toolbar_title_now_playing_movies));
                closeDrawer();
                return true;

            case R.id.popularMovies:
                moviesListFragment.showPopularMovies();
                setToolbarTitle(getString(R.string.toolbar_title_popular_movies));
                closeDrawer();
                return true;

            case R.id.upcomingMovies:
                moviesListFragment.showUpcomingMovies();
                setToolbarTitle(getString(R.string.toolbar_title_upcoming_movies));
                closeDrawer();
                return true;


            case R.id.settings:
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }


        closeDrawer();
        return false;
    }


    /**
     * Helper Methods
     */
    private void setupToolbar() {
        toolbar.setTitle("Movies");
        setSupportActionBar(toolbar);
    }

    private void setupNavigationDrawer() {
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, ((Toolbar) findViewById(R.id.toolbar)), R.string.drawer_open, R.string.drawer_close);
        navigationView.setNavigationItemSelectedListener(this);
        actionBarDrawerToggle.syncState();
    }

    private void closeDrawer() {
        drawerLayout.closeDrawer(Gravity.START);
    }

    private void SHOW_LOG(String message) {
        Log.i("123456", message);
    }

    public boolean isDrawerOpen() {
        return drawerLayout.isDrawerOpen(Gravity.START);
    }

    public void setToolbarTitle(String title) {
        if (getSupportActionBar() != null) getSupportActionBar().setTitle(title);
        else Toast.makeText(this, "Support Action Bar is null", Toast.LENGTH_SHORT).show();
    }

    private void setDefaultMoviesListToolbarTitle() {
        String defaultValue = PreferenceManager.getDefaultSharedPreferences(this).getString(getString(R.string.default_sort), null);

        if (defaultValue != null) {
            if (defaultValue.equals(getString(R.string.value_popular_movies))) {
                setToolbarTitle(getString(R.string.toolbar_title_popular_movies));
            } else if (defaultValue.equals(getString(R.string.value_now_playing_movies))) {
                setToolbarTitle(getString(R.string.toolbar_title_now_playing_movies));
            } else if (defaultValue.equals(getString(R.string.value_upcoming_movies))) {
                setToolbarTitle(getString(R.string.toolbar_title_upcoming_movies));
            } else if (defaultValue.equals(getString(R.string.value_favourite_movies))) {
                Toast.makeText(this, "Showing favourite movies", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(this, "Default Sort value is Null", Toast.LENGTH_SHORT).show();
            setToolbarTitle(getString(R.string.toolbar_title_popular_movies));
        }
    }
}
