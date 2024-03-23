package com.example.diplom_jkh.http
import com.example.diplom_jkh.data.LoginResponse
import com.example.diplom_jkh.data.user.UserAuth
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("login")
    fun loginUser(@Body userAuth: UserAuth): Call<LoginResponse>
}
