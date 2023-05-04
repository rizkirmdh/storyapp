package com.example.mystories

import androidx.lifecycle.ViewModel
import com.example.mystories.remote.LoginRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val loginRepository: LoginRepository) : ViewModel() {
    fun getToken(): Flow<String?> = loginRepository.getToken()

}