package com.app.imagelisting.Screens.Home

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.app.imagelisting.data.Repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class HomeViewModel  @Inject constructor(repository : Repository)
    :ViewModel(){
        val  getAllImages = repository.getAllImages()
}