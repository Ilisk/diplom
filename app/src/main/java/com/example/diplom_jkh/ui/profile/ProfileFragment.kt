package com.example.diplom_jkh.ui.profile

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.diplom.http.RetrofitClient
import com.example.diplom_jkh.R
import com.example.diplom_jkh.data.MainViewModel
import com.example.diplom_jkh.data.MyApp
import com.example.diplom_jkh.data.user.TokenManager
import com.example.diplom_jkh.data.user.UserData
import com.example.diplom_jkh.http.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragment : Fragment() {
    private lateinit var mainViewModel: MainViewModel
    private lateinit var surname: TextView
    private lateinit var name: TextView
    private lateinit var middleName: TextView
    private lateinit var phoneNumber: TextView
    private lateinit var email: TextView
    private lateinit var fullAddress: TextView
    private lateinit var userData: UserData

    companion object {
        fun newInstance() = ProfileFragment()
    }

    private lateinit var viewModel: ProfileViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myApp = requireActivity().application as MyApp
        val rootView = inflater.inflate(R.layout.fragment_profile, container, false)
        mainViewModel = myApp.mainViewModel
        surname = rootView.findViewById(R.id.surname)
        name = rootView.findViewById(R.id.name)
        middleName = rootView.findViewById(R.id.middle_name)
        phoneNumber = rootView.findViewById(R.id.phone_number)
        email = rootView.findViewById(R.id.email)
        fullAddress = rootView.findViewById(R.id.full_address)

        mainViewModel.userData?.let { userData ->
            surname.text = userData.userFullName.surname
            name.text = userData.userFullName.name
            middleName.text = userData.userFullName.middleName
            phoneNumber.text = userData.phoneNumber
            email.text = userData.email
            fullAddress.text = userData.address.getFullAddress()
        }


        return rootView
    }

    private fun loadUserDataFromServer() {
        mainViewModel.userAuth?.let { userAuth ->
            val apiService = RetrofitClient.createService(ApiService::class.java)
            val token = TokenManager.getToken(requireContext())
            if (token != null) {

                // Выполнение запроса с учетом токена аутентификации
                apiService.getUserProfile(token).enqueue(object : Callback<UserData> {
                    override fun onResponse(call: Call<UserData>, response: Response<UserData>) {
                        if (response.isSuccessful) {
                            mainViewModel.userData = response.body()
                            userData = response.body()!!
                            userData.let {
                                surname.text = it.userFullName.surname
                                name.text = it.userFullName.name
                                middleName.text = it.userFullName.middleName
                                phoneNumber.text = it.phoneNumber
                                email.text = it.email
                                fullAddress.text = it.address.getFullAddress()
                            }
                        } else {
                            // Обработать ошибку загрузки
                            Toast.makeText(
                                requireContext(),
                                "Ошибка загрузки данных с сервера",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    override fun onFailure(call: Call<UserData>, t: Throwable) {
                        // Обработать ошибку
                        Toast.makeText(
                            requireContext(),
                            "Ошибка загрузки данных: ${t.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })
            } else {
                // Если токен отсутствует, обработайте эту ситуацию соответствующим образом
                Toast.makeText(
                    requireContext(),
                    "Отсутствует токен аутентификации",
                    Toast.LENGTH_SHORT
                ).show()
            }

        } ?: run {
            // Обновить пользовательский интерфейс, если данные о пользователе недоступны
            Toast.makeText(requireContext(), "Данные о пользователе недоступны", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        // TODO: Use the ViewModel
    }

}