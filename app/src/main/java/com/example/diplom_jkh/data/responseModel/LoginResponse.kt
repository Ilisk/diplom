package com.example.diplom_jkh.data.responseModel

data class LoginResponse (
    val success: Boolean,
    val token: String?,
    val message: String,
)
