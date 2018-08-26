package adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shahzaib.movies.R;

import java.util.List;

import model.Movie;
import model.Result;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {

    private List<Result> moviesResults;
    private Context context;
    private OnItemClickListener onItemClickListener;
    private boolean shouldMovieInfoVisible;


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.single_item_moives_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        // binding data to each item to the UI
        final Result currentMovieResult = moviesResults.get(position);
        holder.movieTitle.setText(currentMovieResult.getTitle());
        holder.movieRating.setText(String.valueOf(currentMovieResult.getVoteAverage()));
        Glide.with(context).load(currentMovieResult.getPosterPath()).into(holder.moviePoster);

        setViewVisible(holder.movie_info_container,shouldMovieInfoVisible);






        //click listeners
        holder.moviePoster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.onMovieItemClick(String.valueOf(currentMovieResult.getId()));
            }
        });

    }


    @Override
    public int getItemCount() {
        if (moviesResults != null) {
            return moviesResults.size();
        } else {
            return 0;
        }
    }


    /************ Setters */
    public void setData(List<Result> moviesResults) {
        this.moviesResults = moviesResults;
    }
    public void setListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }
    public void setMovieInfoVisibility(boolean shouldMovieInfoVisible) {
        this.shouldMovieInfoVisible = shouldMovieInfoVisible;
    }









    /*********** helper methods*/
    void SHOW_LOG(String message) {
        Log.i("123456", message);
    }
    private void setViewVisible(View view, boolean shouldViewVisible) {
        if (view == null) return;
        if (shouldViewVisible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
    }























    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView moviePoster;
        TextView movieTitle, movieRating;
        View movie_info_container;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            moviePoster = itemView.findViewById(R.id.movie_poster_IV);
            movieTitle = itemView.findViewById(R.id.movie_title);
            movieRating = itemView.findViewById(R.id.movie_rating_TV);
            movie_info_container = itemView.findViewById(R.id.movie_info_container);

        }
    }

    public interface OnItemClickListener {
        void onMovieItemClick(String movieID);
    }

}
