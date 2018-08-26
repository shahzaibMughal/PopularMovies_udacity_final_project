package model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ReviewContent {
    @SerializedName("author")
    @Expose
    private String reviewAuthor;

    @SerializedName("content")
    @Expose
    private String reviewContent;


    public ReviewContent(String reviewAuthor, String reviewContent) {
        this.reviewAuthor = reviewAuthor;
        this.reviewContent = reviewContent;
    }


    public String getReviewAuthorName() {
        return reviewAuthor;
    }

    public String getReviewContent() {
        return reviewContent;
    }

}