package activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

import com.example.shahzaib.movies.R;

import java.util.ArrayList;
import java.util.List;

import adapter.ReviewsAdapter;
import model.Review;
import network.ApiClient;
import network.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieReviewsActivity extends AppCompatActivity {

    public static final String KEY_INTENT_MOVIE_ID = "movieID";
    RecyclerView reviewsRecyclerView;
    ReviewsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        reviewsRecyclerView = findViewById(R.id.reviewsRecyclerView);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ReviewsAdapter();


        // initial Setup
        String movieID = getIntent().getStringExtra(KEY_INTENT_MOVIE_ID);
        ApiClient.getClient().create(ApiInterface.class).getReviews(movieID).enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                adapter.setData(response.body().getReviewContentList());
                reviewsRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                SHOW_LOG("Fetching review data failed: "+t.toString());
            }
        });

    }

    private void SHOW_LOG(String message) {
        Log.i("123456", message);
    }
}
