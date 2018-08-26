package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import network.URLs;

public class MovieDetail {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("poster_path")
    @Expose
    private String posterPath;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("original_language")
    @Expose
    private String orignalLanguage;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("vote_average")
    @Expose
    private float voteAverage;

    @SerializedName("genres")
    @Expose
    private List<Genres> genresList;

    @SerializedName("backdrop_path")
    @Expose
    private String backdropPath;




    public MovieDetail(String title, String posterPath, String status, String orignalLanguage, String overview, float voteAverage, List<Genres> genresList, String backdropPath) {
        this.title = title;
        this.posterPath = posterPath;
        this.status = status;
        this.orignalLanguage = orignalLanguage;
        this.overview = overview;
        this.voteAverage = voteAverage;
        this.genresList = genresList;
        this.backdropPath = backdropPath;
    }


    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return URLs.IMAGE_BASE_URL+posterPath;
    }

    public String getStatus() {
        return status;
    }

    public String getOrignalLanguage() {
        return orignalLanguage;
    }

    public String getOverview() {
        return overview;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public List<Genres> getGenresList() {
        return genresList;
    }

    public String getBackdropPath() {
        return URLs.IMAGE_BASE_URL_HIGH_QUALITY+backdropPath;
    }
}
