package com.alex_bystrov.test.data.remote.auth

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AuthRepositoryImpl : AuthRepository {

    private val auth = Firebase.auth

    private var _authState: MutableStateFlow<AuthAnswer> = MutableStateFlow(AuthAnswer.Init)
    val authState: StateFlow<AuthAnswer> get() = _authState.asStateFlow()

    override val currentUserId: String
        get() = auth.currentUser?.uid.orEmpty()

    override suspend fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                _authState.update {
                    AuthAnswer.Succeed(
                        userId = auth.uid.orEmpty()
                    )
                }
            }
            .addOnFailureListener { exception ->
                _authState.update {
                    AuthAnswer.Error(exception.message.toString())
                }
            }
    }

    override suspend fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnSuccessListener {
                _authState.update {
                    AuthAnswer.Succeed(
                        userId = auth.uid.orEmpty()
                    )
                }
            }
            .addOnFailureListener { exception ->
                _authState.update {
                    AuthAnswer.Error(exception.message.toString())
                }
            }
    }

    override suspend fun signOut() {
        auth.signOut()
    }
}