package com.alex_bystrov.test.data.remote.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.auth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

object AuthRepositoryImpl : AuthRepository {

    override val currentUserId: String
        get() = Firebase.auth.currentUser?.uid.orEmpty()

    override suspend fun signIn(email: String, password: String): Task<AuthResult> {
        return Firebase.auth.signInWithEmailAndPassword(email, password)
    }

    override suspend fun signUp(email: String, password: String) {
        Firebase.auth.createUserWithEmailAndPassword(email, password)
    }

    override suspend fun signOut() {
        Firebase.auth.signOut()
    }
}