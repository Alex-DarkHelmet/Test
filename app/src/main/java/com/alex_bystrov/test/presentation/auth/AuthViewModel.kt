package com.alex_bystrov.test.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alex_bystrov.test.data.remote.auth.AuthAnswer
import com.alex_bystrov.test.data.remote.auth.AuthRepositoryImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthViewModel : ViewModel() {
    private val repository = AuthRepositoryImpl

    private var _authState: MutableStateFlow<AuthAnswer> = MutableStateFlow(AuthAnswer.Init)
    val authState: StateFlow<AuthAnswer> get() = _authState.asStateFlow()

    fun signInResponse(email: String, password: String) {
        CoroutineScope(Job() + Dispatchers.IO).launch {
            val response = repository.signIn(email = email, password = password)
            response
                .addOnSuccessListener {
                _authState.update {
                    AuthAnswer.Succeed(
                        response.result.user?.uid.orEmpty()
                    )
                }
            }
                .addOnFailureListener {exception ->
                    _authState.update {
                        AuthAnswer.Error(exception.message.toString())
                    }
                }
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