package com.alex_bystrov.test.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex_bystrov.test.data.remote.auth.AuthAnswer
import com.alex_bystrov.test.data.remote.auth.AuthRepositoryImpl
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val repository = AuthRepositoryImpl()

    val authState: StateFlow<AuthAnswer> get() = repository.authState
    val currentUserId: String get() = repository.currentUserId

    fun signInResponse(email: String, password: String) {
        viewModelScope.launch {
            repository.signIn(email = email, password = password)
//            Log.i("AuthViewModel", "Current id - $currentUserId")
        }
    }

    fun createAccountResponse(email: String, password: String) {
        viewModelScope.launch {
            repository.signUp(email = email, password = password)
        }
    }

    fun singOut() {
        viewModelScope.launch {
            repository.signOut()
        }
    }

}