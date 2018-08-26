package adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.shahzaib.movies.R;

import java.util.List;

import model.Review;
import model.ReviewContent;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {

    List<ReviewContent> reviewsList;


    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_movie_reviews, parent, false);
        return new ReviewViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        holder.reviewAuthor.setText(reviewsList.get(position).getReviewAuthorName());
        holder.reviewContent.setText(reviewsList.get(position).getReviewContent());
    }


    @Override
    public int getItemCount() {
        if (reviewsList != null) return reviewsList.size();
        else return 0;
    }


    public void setData(List<ReviewContent> reviewsList) {
        this.reviewsList = reviewsList;
    }










    class ReviewViewHolder extends RecyclerView.ViewHolder {
        TextView reviewAuthor, reviewContent;

        public ReviewViewHolder(@NonNull View itemView) {
            super(itemView);

            reviewAuthor = itemView.findViewById(R.id.reviewAuthor1);
            reviewContent = itemView.findViewById(R.id.reviewContent1);
        }
    }
}
