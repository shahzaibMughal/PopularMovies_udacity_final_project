package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Movie {

    @SerializedName("results")
    @Expose
    private List<Result> results = null;

    public Movie(List<Result> results) {
        this.results = results;
    }

    public List<Result> getResults() {
        return results;
    }
}