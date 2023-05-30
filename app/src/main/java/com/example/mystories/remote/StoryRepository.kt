package com.example.mystories.remote

import androidx.lifecycle.LiveData
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.mystories.local.entity.Story
import com.example.mystories.local.room.StoryDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@ExperimentalPagingApi
class StoryRepository @Inject constructor(private val apiService: ApiService, private val storyDatabase: StoryDatabase){
    fun getStories(token: String): LiveData<PagingData<Story>> {
        return Pager(
            config = PagingConfig(pageSize = 5),
            remoteMediator = StoryRemoteMediator(storyDatabase, apiService, generateToken(token)),
            pagingSourceFactory = {
                storyDatabase.storyDao().getStories()
            }
        ).liveData
    }

    fun getStoriesWithLocation(token: String): Flow<Result<StoriesResponse>> = flow {
        try {
            val bearerToken = generateToken(token)
            val response = apiService.getStories(bearerToken, size = 30, location = 1)
            emit(Result.success(response))
        } catch (e: Exception){
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }

    suspend fun uploadFile(token: String, file: MultipartBody.Part, description: RequestBody, lat: RequestBody? = null, lon: RequestBody? = null): Flow<Result<UploadResponse>> = flow {
        try {
            val bearerToken = generateToken(token)
            val response = apiService.uploadStory(bearerToken, file, description, lat, lon)
            emit(Result.success(response))
        }catch (e: Exception){
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }

    private fun generateToken(token: String): String{
        return "Bearer $token"
    }
}