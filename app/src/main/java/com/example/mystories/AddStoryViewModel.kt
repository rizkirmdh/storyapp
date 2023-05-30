package com.example.mystories

import androidx.lifecycle.ViewModel
import androidx.paging.ExperimentalPagingApi
import com.example.mystories.remote.LoginRepository
import com.example.mystories.remote.StoryRepository
import com.example.mystories.remote.UploadResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@ExperimentalPagingApi
@HiltViewModel
class AddStoryViewModel @Inject constructor(private val loginRepository: LoginRepository, private val storyRepository: StoryRepository) : ViewModel(){
    fun getToken(): Flow<String?> = loginRepository.getToken()

    suspend fun uploadImage(token: String, file: MultipartBody.Part, desc: RequestBody, lat: RequestBody?, lon: RequestBody?): Flow<Result<UploadResponse>> = storyRepository.uploadFile(token, file, desc, lat, lon)
}