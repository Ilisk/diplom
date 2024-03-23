package com.example.diplom_jkh.data.user

data class UserData(
    val authData: UserAuth,
    val email: String,
    val phoneNumber: String,
    val address: String,
    val userFullName: UserFullName
)

