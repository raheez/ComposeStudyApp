package com.app.imagelisting.data.Repository

import androidx.paging.*
import com.app.imagelisting.data.local.UnsplashDatabase
import com.app.imagelisting.data.paging.SearchPagingSource
import com.app.imagelisting.data.paging.UnsplashRemoteMediator
import com.app.imagelisting.data.remote.UnsplashApi
import com.app.imagelisting.model.UnsplashImage
import com.app.imagelisting.util.Constants.ITEMS_PER_PAGE
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ExperimentalPagingApi
class Repository @Inject constructor(
    private val unsplashApi: UnsplashApi,
    private val unsplashDatabase: UnsplashDatabase
) {

    fun getAllImages(): Flow<PagingData<UnsplashImage>> {
        val pagingSourceFactory = {unsplashDatabase.unsplashImageDao().getAllImages()}
        return Pager(
            config = PagingConfig(ITEMS_PER_PAGE),
            remoteMediator = UnsplashRemoteMediator(
                unsplashApi = unsplashApi,
                unsplashDatabase = unsplashDatabase
            ),
            pagingSourceFactory =pagingSourceFactory
        ).flow
    }

    fun searchImages(query: String):Flow<PagingData<UnsplashImage>>{
        return Pager(
            config = PagingConfig(pageSize = ITEMS_PER_PAGE),
            pagingSourceFactory = {
                SearchPagingSource(unsplashApi = unsplashApi,query = query)
            }
        ).flow
    }
}