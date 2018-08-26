package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MovieVideoResult {

    @SerializedName("key")
    @Expose
    private String key;


    public MovieVideoResult(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
