package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Review {

    @SerializedName("results")
    @Expose
    private List<ReviewContent> reviewContentList;


    public Review(List<ReviewContent> reviewContentList) {
        this.reviewContentList = reviewContentList;
    }

    public List<ReviewContent> getReviewContentList() {
        return reviewContentList;
    }
}
