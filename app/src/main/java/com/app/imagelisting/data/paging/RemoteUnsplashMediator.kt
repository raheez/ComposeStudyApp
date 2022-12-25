package com.app.imagelisting.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.app.imagelisting.data.local.UnsplashDatabase
import com.app.imagelisting.data.remote.UnsplashApi
import com.app.imagelisting.model.UnsplashImage
import com.app.imagelisting.model.UnsplashRemoteKeys

@ExperimentalPagingApi
class RemoteUnsplashMediator(
    private val unsplashApi:UnsplashApi,
    private val unsplashDatabase: UnsplashDatabase
) :RemoteMediator<Int,UnsplashImage>(){

    private val unsplashImageDao =unsplashDatabase.unsplashImageDao()
    private val unsplashRemoteKeyDao =unsplashDatabase.unsplashRemoteKeyDao()

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, UnsplashImage>
    ): MediatorResult {


        return try{

            val currentPAge = when(loadType){
                LoadType.REFRESH ->{

                    val remoteKeys = getRemoteKeyCloseToCurrentPosition(state)
                    remoteKeys?.nextPage?.minus(1)?:1
                }
                LoadType.PREPEND ->{

                    val remoteKeys = getRemoteKeyForFirstTime(state)
                    val prevPage = remoteKeys?.prevPage
                        ?:return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys!=null
                        )
                    prevPage
                }
                LoadType.APPEND ->{

                    val remoteKeys = getRemoteKeyForLastTime(state)
                    val nextPage = remoteKeys?.nextPage
                        ?:return MediatorResult.Success(
                            endOfPaginationReached = remoteKeys !=null
                        )
                    nextPage
                }


            }
            val response = unsplashApi.getAllImages(page = currentPAge, perPage = 10)
            val endOfPaginationReached = response.isEmpty()

            val prevPage = if(currentPAge == 1) null else currentPAge -1
            val nextPage = if(endOfPaginationReached) null else currentPAge +1

            unsplashDatabase?.withTransaction {
                if (loadType == LoadType.REFRESH){
                    unsplashImageDao.deleteAllImages()
                    unsplashRemoteKeyDao.deleteAllRemoteKeys()
                }
                val keys = response.map {
                    unsplashImage ->

                    UnsplashRemoteKeys(
                        id = unsplashImage.id,
                        prevPage = prevPage,
                        nextPage = nextPage
                    )
                }
                unsplashRemoteKeyDao?.addAllRemoteKeys(remoteKeys = keys)
                unsplashImageDao?.addImages(images = response)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        }catch (e:Exception){
            return MediatorResult.Error(e)
        }
    }

    private suspend fun getRemoteKeyCloseToCurrentPosition(state: PagingState<Int, UnsplashImage>)
    :UnsplashRemoteKeys?{

        return state.anchorPosition?.let {
            position ->
            state.closestItemToPosition(position)?.id?.let {
                id->unsplashRemoteKeyDao.getRemoteKeys(id = id)
            }
        }
    }

    private suspend fun getRemoteKeyForFirstTime(
        state: PagingState<Int, UnsplashImage>
    ):UnsplashRemoteKeys?{
        return state.pages.firstOrNull{
            it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { unsplashImage ->
                unsplashRemoteKeyDao.getRemoteKeys(id = unsplashImage.id)
            }
    }

    private suspend fun getRemoteKeyForLastTime(
        state: PagingState<Int, UnsplashImage>
    ):UnsplashRemoteKeys?{
        return state.pages.lastOrNull{
            it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { unsplashImage ->
                unsplashRemoteKeyDao.getRemoteKeys(id = unsplashImage.id)
            }
    }


}