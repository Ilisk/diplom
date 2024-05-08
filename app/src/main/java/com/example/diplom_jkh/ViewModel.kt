package com.example.diplom_jkh

import androidx.lifecycle.ViewModel
import com.example.diplom_jkh.data.responseModel.LoginResponse
import com.example.diplom_jkh.data.user.UserAuth

class ViewModel : ViewModel() {
    var userAuth: UserAuth? = null
    var loginResponse: LoginResponse? = null
}