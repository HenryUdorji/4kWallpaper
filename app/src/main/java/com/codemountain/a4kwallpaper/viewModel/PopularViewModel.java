package com.codemountain.a4kwallpaper.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.codemountain.a4kwallpaper.model.Hit;
import com.codemountain.a4kwallpaper.repository.PopularDataSourceFactory;

import static com.codemountain.a4kwallpaper.utils.Constants.PAGE_SIZE;

public class PopularViewModel extends ViewModel {

    //creating liveData for pagedList pagedKeyedDataSource
    public LiveData<PagedList<Hit>> popularPagedList;
    public LiveData<PageKeyedDataSource<Integer, Hit>> liveDataSource;

    public PopularViewModel() {
        PopularDataSourceFactory popularDataSourceFactory = new PopularDataSourceFactory();
        liveDataSource = popularDataSourceFactory.getPopularLiveDataSource();

        //get pagedListConfig
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                    .setEnablePlaceholders(false)
                    .setPageSize(PAGE_SIZE)
                    .build();

        //building the pagedList
        popularPagedList = (new LivePagedListBuilder(popularDataSourceFactory, pagedListConfig)).build();
    }
}
