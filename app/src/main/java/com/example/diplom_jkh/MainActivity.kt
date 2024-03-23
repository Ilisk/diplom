package com.example.diplom_jkh

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.diplom_jkh.http.ApiService
import com.example.diplom.http.RetrofitClient
import com.example.diplom_jkh.data.LoginResponse
import com.example.diplom_jkh.data.user.UserAuth
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private val apiService = RetrofitClient.createService(ApiService::class.java)

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val accountNumber: EditText = findViewById(R.id.account_number)
        val password: EditText = findViewById(R.id.password)
        val btnLogin = findViewById<Button>(R.id.login)

        btnLogin.setOnClickListener {
            val intent = Intent(this, NavigationActivity::class.java)
            val account = accountNumber.getText().toString().trim()
            val pass = password.getText().toString().trim()
            val userAuth = UserAuth(account, pass)
            startActivity(intent)
//            apiService.loginUser(userAuth).enqueue(object : Callback<LoginResponse> {
//                override fun onResponse(call: Call<LoginResponse?>, response: Response<LoginResponse?>) {
//                    if (response.isSuccessful) {
//                        val loginResponse = response.body()
//                        if (loginResponse != null && loginResponse.success) {
//                            // Обработка успешного входа
//                            Toast.makeText(this@MainActivity, "Успешный вход", Toast.LENGTH_SHORT).show()
//                            startActivity(intent)
//                            // Далее обработайте ответ сервера и выполните необходимые действия
//                        } else {
//                            // Обработка неуспешного входа
//                            Toast.makeText(this@MainActivity, "Неверные учетные данные", Toast.LENGTH_SHORT).show()
//                        }
//                    } else {
//                        // Обработка ошибки запроса
//                        Toast.makeText(this@MainActivity, "Ошибка запроса", Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//                override fun onFailure(call: Call<LoginResponse?>, t: Throwable) {
//                    // Обработка ошибки сети
//                    Toast.makeText(this@MainActivity, "Ошибка сети", Toast.LENGTH_SHORT).show()
//                }
//            })
        }


    }
}


