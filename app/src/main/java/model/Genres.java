package model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Genres {

    @SerializedName("name")
    @Expose
    private String name;


    public Genres(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
