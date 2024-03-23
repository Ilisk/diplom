package com.example.diplom_jkh.data.user

import com.google.gson.annotations.SerializedName

data class UserAuth(
    @SerializedName("username") val username: String,
    @SerializedName("password") val password: String
)
