package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieVideo {

    @SerializedName("results")
    @Expose
    private List<MovieVideoResult> movieVideoResultList;

    public MovieVideo(List<MovieVideoResult> movieVideoResultList) {
        this.movieVideoResultList = movieVideoResultList;
    }

    public List<MovieVideoResult> getMovieVideoResultList() {
        return movieVideoResultList;
    }
}
