package com.codemountain.a4kwallpaper.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.codemountain.a4kwallpaper.model.Hit;

public class PopularDataSourceFactory extends DataSource.Factory {
    private MutableLiveData<PageKeyedDataSource<Integer, Hit>> popularLiveDataSource = new MutableLiveData<>();


    @NonNull
    @Override
    public DataSource<Integer, Hit> create() {
        PopularDataSource popularDataSource = new PopularDataSource();

        //posting the datasource to get values
        popularLiveDataSource.postValue(popularDataSource);

        return popularDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Hit>> getPopularLiveDataSource() {
        return popularLiveDataSource;
    }
}
