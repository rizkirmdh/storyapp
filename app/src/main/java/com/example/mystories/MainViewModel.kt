package com.example.mystories

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mystories.local.entity.Story
import com.example.mystories.remote.LoginRepository
import com.example.mystories.remote.StoriesResponse
import com.example.mystories.remote.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class MainViewModel @Inject constructor(private val loginRepository: LoginRepository? = null, private val storyRepository: StoryRepository) : ViewModel() {
    fun getToken(): Flow<String?>? = loginRepository?.getToken()

    fun saveToken(token: String){
        viewModelScope.launch {
            loginRepository?.saveToken(token)
        }
    }

    fun getStories(token: String): LiveData<PagingData<Story>> = storyRepository.getStories(token).cachedIn(viewModelScope)

}