package com.codemountain.a4kwallpaper.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.codemountain.a4kwallpaper.model.Hit;

public class LatestDataSourceFactory extends DataSource.Factory {
    private MutableLiveData<PageKeyedDataSource<Integer, Hit>> latestLiveDataSource = new MutableLiveData<>();


    @NonNull
    @Override
    public DataSource<Integer, Hit> create() {
        LatestDataSource latestDataSource = new LatestDataSource();

        //posting the datasource to get values
        latestLiveDataSource.postValue(latestDataSource);

        return latestDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Hit>> getLatestLiveDataSource() {
        return latestLiveDataSource;
    }
}
