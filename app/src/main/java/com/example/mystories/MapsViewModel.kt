package com.example.mystories

import androidx.lifecycle.ViewModel
import com.example.mystories.remote.StoriesResponse
import com.example.mystories.remote.StoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(private val storyRepository: StoryRepository) : ViewModel() {

    fun getStoriesWithLocation(token: String): Flow<Result<StoriesResponse>> = storyRepository.getStoriesWithLocation(token)

}