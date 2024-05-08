package com.example.diplom_jkh.http

import com.example.diplom_jkh.data.request.RequestData
import com.example.diplom_jkh.data.responseModel.LoginResponse
import com.example.diplom_jkh.data.user.UserAuth
import com.example.diplom_jkh.data.user.UserData
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @POST("login")
    fun loginUser(@Body userAuth: UserAuth): Call<LoginResponse>

    @POST("requests")
    fun getUserRequests(@Body userAuth: UserAuth): Call<ServerResponse>

    @POST("notifications")
    fun getUserNotifications(@Body userAuth: UserAuth): Call<ServerResponse>


    @GET("profile")
    fun getUserProfile(@Header("Authorization") token: String): Call<UserData>

    @GET("request_detailed")
    fun getDetailedRequest(
        @Header("Authorization") token: String,
        @Query("request_number") requestNumber: Int
    ): Call<RequestData>


    @POST("create_request")
    fun createRequest(@Body requestData: RequestData): Call<Void>


}
