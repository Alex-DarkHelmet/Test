package com.alex_bystrov.test.data.remote.auth

sealed class AuthAnswer {
    data class Succeed(val userId: String): AuthAnswer()
    data class Error(val message: String): AuthAnswer()
    object Loading: AuthAnswer()
    object Init: AuthAnswer()
}