package com.codemountain.a4kwallpaper.repository;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.paging.PageKeyedDataSource;

import com.codemountain.a4kwallpaper.model.Hit;
import com.codemountain.a4kwallpaper.model.Latest;
import com.codemountain.a4kwallpaper.model.Popular;
import com.codemountain.a4kwallpaper.repository.retrofit.RetrofitClient;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.codemountain.a4kwallpaper.utils.Constants.EDITORS_CHOICE;
import static com.codemountain.a4kwallpaper.utils.Constants.FIRST_PAGE;
import static com.codemountain.a4kwallpaper.utils.Constants.LATEST;
import static com.codemountain.a4kwallpaper.utils.Constants.PAGE_SIZE;
import static com.codemountain.a4kwallpaper.utils.Constants.POPULAR;
import static com.codemountain.a4kwallpaper.utils.Constants.SAFE_SEARCH;

public class LatestDataSource extends PageKeyedDataSource<Integer, Hit>{
    private static final String TAG = "LatestDataSource";


    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Hit> callback) {
        RetrofitClient.getInstance().getApi()
                .getLatest(LATEST, FIRST_PAGE, PAGE_SIZE, SAFE_SEARCH, EDITORS_CHOICE)
                .enqueue(new Callback<Latest>() {
                    @Override
                    public void onResponse(Call<Latest> call, Response<Latest> response) {
                        if (response.body() != null) {
                            callback.onResult(response.body().hits, null, FIRST_PAGE + 1);
                            Log.e(TAG, "onResponse: initial loaded: " + response.body().hits);
                        }
                        else {
                            try {
                                Log.e(TAG, "onResponse: initial Error " + response.errorBody().string());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<Latest> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Hit> callback) {
        RetrofitClient.getInstance().getApi()
                .getLatest(LATEST, params.key, PAGE_SIZE, SAFE_SEARCH, EDITORS_CHOICE)
                .enqueue(new Callback<Latest>() {
                    @Override
                    public void onResponse(Call<Latest> call, Response<Latest> response) {
                        /**
                         * if the current page is greater than 1, we decrement
                         * the page number else there is no previous page
                         */
                        Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
                        if (response.body() != null) {
                            callback.onResult(response.body().hits, adjacentKey);
                            Log.e(TAG, "onResponse: before data loaded");
                        }
                        else {
                            Log.e(TAG, "onResponse: before Error: " + response.errorBody().toString());
                        }
                    }

                    @Override
                    public void onFailure(Call<Latest> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Hit> callback) {
        RetrofitClient.getInstance().getApi()
                .getLatest(LATEST, params.key, PAGE_SIZE, SAFE_SEARCH, EDITORS_CHOICE)
                .enqueue(new Callback<Latest>() {
                    @Override
                    public void onResponse(Call<Latest> call, Response<Latest> response) {
                        if (response.body() != null) {
                            /**
                             * if params.key is still less than response.body().totalHits
                             * means there is still a next page
                             * so increment the next page number
                             */
                            Integer key = (params.key < response.body().totalHits) ? params.key + 1 : null;
                            callback.onResult(response.body().hits, key);
                        }
                        else {
                            Log.e(TAG, "onResponse: loadAfter Error");
                        }
                    }

                    @Override
                    public void onFailure(Call<Latest> call, Throwable t) {
                        Log.e(TAG, "onFailure: " + t.getMessage());
                    }
                });
    }
}
