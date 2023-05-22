package com.example.mystories.remote

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

class StoryRepository @Inject constructor(private val apiService: ApiService){
    suspend fun getStories(token: String, page: Int? = null, size: Int? = null): Flow<Result<StoriesResponse>> = flow {
        try {
            val bearerToken = generateToken(token)
            val response = apiService.getStories(bearerToken, page, size)
            emit(Result.success(response))
        }catch (e: Exception){
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

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

    suspend fun uploadFile(token: String, file: MultipartBody.Part, description: RequestBody): Flow<Result<UploadResponse>> = flow {
        try {
            val bearerToken = generateToken(token)
            val response = apiService.uploadStory(bearerToken, file, description)
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