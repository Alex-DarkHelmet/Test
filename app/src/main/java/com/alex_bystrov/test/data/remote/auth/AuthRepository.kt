package com.alex_bystrov.test.data.remote.auth

import com.google.firebase.Firebase

interface AuthRepository {

    val currentUserId: String

    suspend fun signIn(email: String, password: String)

    suspend fun signUp(email: String, password: String)

    suspend fun signOut()
}
