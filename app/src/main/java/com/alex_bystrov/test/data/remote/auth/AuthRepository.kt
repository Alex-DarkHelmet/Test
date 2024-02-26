package com.alex_bystrov.test.data.remote.auth

import com.google.android.gms.tasks.Task
import com.google.firebase.Firebase
import com.google.firebase.auth.AuthResult

interface AuthRepository {

    val currentUserId: String

    suspend fun signIn(email: String, password: String): Task<AuthResult>

    suspend fun signUp(email: String, password: String)

    suspend fun signOut()
}
