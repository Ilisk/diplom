package com.example.diplom_jkh

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.diplom_jkh.http.ApiService
import com.example.diplom.http.RetrofitClient
import com.example.diplom_jkh.data.MainViewModel
import com.example.diplom_jkh.data.MyApp
import com.example.diplom_jkh.data.responseModel.LoginResponse
import com.example.diplom_jkh.data.user.TokenManager
import com.example.diplom_jkh.data.user.UserAuth
import com.example.diplom_jkh.data.user.UserData
import com.example.diplom_jkh.http.ServerDataManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val apiService = RetrofitClient.createService(ApiService::class.java)
    //private val myApp = application as MyApp
    //private lateinit var myApp: MyApp
   // private val mainViewModel = myApp.mainViewModel
    private var token = ""
    //val serverDataManager = ServerDataManager(mainViewModel, this@MainActivity)


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val myApp = application as MyApp
        val mainViewModel = myApp.mainViewModel
        val accountNumber: EditText = findViewById(R.id.account_number)
        val password: EditText = findViewById(R.id.password)
        val btnLogin = findViewById<Button>(R.id.login)
        btnLogin.setOnClickListener {
            val intent = Intent(this, NavigationActivity::class.java)
            val account = accountNumber.getText().toString().trim()
            val pass = password.getText().toString().trim()
            val userAuth = UserAuth(account, pass)
            mainViewModel.userAuth = userAuth
            Toast.makeText(
                this@MainActivity,
                mainViewModel.userAuth!!.username,
                Toast.LENGTH_SHORT
            ).show()
            apiService.loginUser(userAuth).enqueue(object : Callback<LoginResponse> {
                override fun onResponse(call: Call<LoginResponse?>, response: Response<LoginResponse?>) {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        if (loginResponse != null && loginResponse.success) {
                            // Обработка успешного входа
                            Toast.makeText(this@MainActivity, "Успешный вход", Toast.LENGTH_SHORT).show()
                            token = loginResponse.token.toString()
                            if (token != null) {
                                // Сохраняем токен в SharedPreferences
                                TokenManager.saveToken(this@MainActivity, token)
                                //loadUserDataFromServer()
                                ServerDataManager(mainViewModel, this@MainActivity).loadUserDataFromServer()
                            }
                            startActivity(intent)
                            // Далее обработайте ответ сервера и выполните необходимые действия
                        } else {
                            // Обработка неуспешного входа
                            Toast.makeText(this@MainActivity, loginResponse?.message ?: "Неверные учетные данные", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        // Обработка ошибки запроса
                        Toast.makeText(this@MainActivity, "Ошибка запроса", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
                    // Обработка ошибки сети
                    Toast.makeText(this@MainActivity, "Ошибка сети", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun loadUserDataFromServer() {
        val myApp = application as MyApp
        val mainViewModel = myApp.mainViewModel
        mainViewModel.userAuth?.let { userAuth ->
            apiService.getUserProfile(token).enqueue(object : Callback<UserData> {
                override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                    if (response.isSuccessful) {
                        mainViewModel.userData = response.body()
                    } else {
                        Toast.makeText(
                            this@MainActivity,
                            "Ошибка загрузки данных с сервера",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<UserData>, t: Throwable) {
                    Toast.makeText(
                        this@MainActivity,
                        "Ошибка загрузки данных: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })

        } ?: run {
            Toast.makeText(this@MainActivity, "Данные о пользователе недоступны", Toast.LENGTH_SHORT)
                .show()
        }
    }


}


