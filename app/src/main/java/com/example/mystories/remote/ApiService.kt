package com.example.mystories.remote

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("/v1/#/login")
    suspend fun accountLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ) : LoginResponse

    @FormUrlEncoded
    @POST("/v1/#/register")
    suspend fun accountRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): RegisterResponse

//    @GET("/v1/#/stories")
//    suspend fun getStories(
//        @Header("Authorization") token: String,
//        @Query("page") page: Int?,
//        @Query("size") size: Int?
//    ): StoriesResponse
//
//    @Multipart
//    @POST("/v1/#/stories")
//    suspend fun uploadStory(
//        @Header("Authorization") token: String,
//        @Part file: MultipartBody.Part,
//        @Part("description") description: RequestBody
//    ): UploadResponse
}