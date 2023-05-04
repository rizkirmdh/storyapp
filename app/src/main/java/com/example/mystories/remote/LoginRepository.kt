package com.example.mystories.remote

import com.example.mystories.local.TokenPreference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class LoginRepository @Inject constructor(private val apiService: ApiService, private val preference: TokenPreference) {
    suspend fun accountLogin(email: String, password: String): Flow<Result<LoginResponse>> = flow {
        try {
            val response = apiService.accountLogin(email, password)
            emit(Result.success(response))
        }catch (e: Exception){
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun accountRegister(name: String, email: String, password: String): Flow<Result<RegisterResponse>> = flow{
        try {
            val response = apiService.accountRegister(name, email, password)
            emit(Result.success(response))
        }catch (e: Exception){
            e.printStackTrace()
            emit(Result.failure(e))
        }
    }.flowOn(Dispatchers.IO)

    suspend fun saveToken(token: String){
        preference.saveToken(token)
    }

    fun getToken(): Flow<String?> = preference.getToken()
}