package com.example.mystories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystories.remote.LoginRepository
import com.example.mystories.remote.StoriesResponse
import com.example.mystories.remote.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val loginRepository: LoginRepository, private val storyRepository: StoryRepository) : ViewModel() {
    fun getToken(): Flow<String?> = loginRepository.getToken()

    fun saveToken(token: String){
        viewModelScope.launch {
            loginRepository.saveToken(token)
        }
    }

    suspend fun getStories(token: String): Flow<Result<StoriesResponse>> = storyRepository.getStories(token, null, null)

}