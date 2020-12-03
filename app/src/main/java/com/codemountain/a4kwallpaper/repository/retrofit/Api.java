package com.codemountain.a4kwallpaper.repository.retrofit;

import com.codemountain.a4kwallpaper.model.Category;
import com.codemountain.a4kwallpaper.model.Latest;
import com.codemountain.a4kwallpaper.model.Popular;
import com.codemountain.a4kwallpaper.model.ViewCategory;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Api {

    @GET("api/?key=18750835-b4f98cca93c1ce4d90e079fec")
    Call<Popular> getPopular(
            @Query("order") String popular,
            @Query("page") int page,
            @Query("per_page") int pageSize,
            @Query("safesearch") String safeSearch,
            @Query("editors_choice") String editorsChoice
    );

    @GET("api/?key=18750835-b4f98cca93c1ce4d90e079fec")
    Call<Latest> getLatest(
            @Query("order") String latest,
            @Query("page") int page,
            @Query("per_page") int pageSize,
            @Query("safesearch") String safeSearch,
            @Query("editors_choice") String editorsChoice
    );

    @GET("api/?key=18750835-b4f98cca93c1ce4d90e079fec")
    Call<ViewCategory> getCategory(
            @Query("category") String latest,
            @Query("page") int page,
            @Query("per_page") int pageSize,
            @Query("safesearch") String safeSearch,
            @Query("editors_choice") String editorsChoice
    );
}
