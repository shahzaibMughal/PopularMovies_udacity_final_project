package model;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import network.URLs;

public class Result {

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("vote_average")
    @Expose
    private Double voteAverage;


    public Result(String posterPath,  Integer id, String title, Double voteAverage) {
        this.posterPath = posterPath;
        this.id = id;
        this.title = title;
        this.voteAverage = voteAverage;
    }




    public String getPosterPath() {
        String completePosterPath = URLs.IMAGE_BASE_URL+posterPath;
        return completePosterPath;
    }
    public Integer getId() {
        return id;
    }
    public String getTitle() {
        return title;
    }
    public Double getVoteAverage() {
        return voteAverage;
    }

}
