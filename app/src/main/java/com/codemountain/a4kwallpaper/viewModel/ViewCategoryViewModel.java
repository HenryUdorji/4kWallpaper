package com.codemountain.a4kwallpaper.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PageKeyedDataSource;
import androidx.paging.PagedList;

import com.codemountain.a4kwallpaper.model.Hit;
import com.codemountain.a4kwallpaper.repository.LatestDataSourceFactory;
import com.codemountain.a4kwallpaper.repository.ViewCategoryDataSourceFactory;

import static com.codemountain.a4kwallpaper.utils.Constants.PAGE_SIZE;

public class ViewCategoryViewModel extends ViewModel {

    //creating liveData for pagedList pagedKeyedDataSource
    public LiveData<PagedList<Hit>> viewCategoryPagedList;
    public LiveData<PageKeyedDataSource<Integer, Hit>> liveDataSource;

    public ViewCategoryViewModel() {
        ViewCategoryDataSourceFactory viewCategoryDataSourceFactory = new ViewCategoryDataSourceFactory();
        liveDataSource = viewCategoryDataSourceFactory.getViewCategoryLiveDataSource();

        //get pagedListConfig
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                    .setEnablePlaceholders(false)
                    .setPageSize(PAGE_SIZE)
                    .build();

        //building the pagedList
        viewCategoryPagedList = (new LivePagedListBuilder(viewCategoryDataSourceFactory, pagedListConfig)).build();
    }
}
