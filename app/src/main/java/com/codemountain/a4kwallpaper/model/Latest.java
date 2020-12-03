package com.codemountain.a4kwallpaper.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Latest {
    @SerializedName("total")
    @Expose
    public int total;
    @SerializedName("totalHits")
    @Expose
    public int totalHits;
    @SerializedName("hits")
    @Expose
    public List<Hit> hits = null;

    public Latest() {
    }
}
