package com.codemountain.a4kwallpaper.repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.codemountain.a4kwallpaper.model.Hit;

public class ViewCategoryDataSourceFactory extends DataSource.Factory {
    private MutableLiveData<PageKeyedDataSource<Integer, Hit>> viewCategoryLiveDataSource = new MutableLiveData<>();


    @NonNull
    @Override
    public DataSource<Integer, Hit> create() {
        ViewCategoryDataSource viewCategoryDataSource = new ViewCategoryDataSource();

        //posting the datasource to get values
        viewCategoryLiveDataSource.postValue(viewCategoryDataSource);

        return viewCategoryDataSource;
    }

    public MutableLiveData<PageKeyedDataSource<Integer, Hit>> getViewCategoryLiveDataSource() {
        return viewCategoryLiveDataSource;
    }
}
