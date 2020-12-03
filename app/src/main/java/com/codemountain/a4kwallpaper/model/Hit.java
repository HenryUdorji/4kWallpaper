package com.codemountain.a4kwallpaper.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hit {

    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("previewURL")
    @Expose
    public String previewURL;
    @SerializedName("webformatURL")
    @Expose
    public String webformatURL;
    @SerializedName("webformatWidth")
    @Expose
    public int webformatWidth;
    @SerializedName("webformatHeight")
    @Expose
    public int webformatHeight;
    @SerializedName("largeImageURL")
    @Expose
    public String largeImageURL;
    @SerializedName("imageWidth")
    @Expose
    public int imageWidth;
    @SerializedName("imageHeight")
    @Expose
    public int imageHeight;
    @SerializedName("imageSize")
    @Expose
    public int imageSize;

    public Hit() {
    }

}
