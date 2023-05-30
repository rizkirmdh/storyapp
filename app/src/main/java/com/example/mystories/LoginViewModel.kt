package com.example.mystories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mystories.remote.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository) : ViewModel() {
    suspend fun accountLogin(email: String, password: String) = loginRepository.accountLogin(email, password)

    fun saveToken(token: String){
        viewModelScope.launch {
            loginRepository.saveToken(token)
        }
    }

    fun getToken(): Flow<String?> = loginRepository.getToken()
}