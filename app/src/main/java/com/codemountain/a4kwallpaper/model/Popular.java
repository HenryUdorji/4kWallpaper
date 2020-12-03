
package com.codemountain.a4kwallpaper.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Popular {

    @SerializedName("total")
    @Expose
    public int total;
    @SerializedName("totalHits")
    @Expose
    public int totalHits;
    @SerializedName("hits")
    @Expose
    public List<Hit> hits = null;

    public Popular() {
    }




}
