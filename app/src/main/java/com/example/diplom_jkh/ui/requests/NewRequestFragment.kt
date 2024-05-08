package com.example.diplom_jkh.ui.requests

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.example.diplom.http.RetrofitClient
import com.example.diplom_jkh.NavigationActivity
import com.example.diplom_jkh.R
import com.example.diplom_jkh.data.MainViewModel
import com.example.diplom_jkh.data.MyApp
import com.example.diplom_jkh.data.request.RequestCategory
import com.example.diplom_jkh.data.request.RequestData
import com.example.diplom_jkh.data.request.RequestStatus
import com.example.diplom_jkh.data.user.TokenManager
import com.example.diplom_jkh.data.user.UserData
import com.example.diplom_jkh.http.ApiService
import com.example.diplom_jkh.http.ServerDataManager
import com.example.diplom_jkh.ui.profile.ProfileFragment
import retrofit2.Call
import retrofit2.Response
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

class NewRequestFragment : Fragment() {
    private val profileFragment = ProfileFragment.newInstance()
    private lateinit var mainViewModel: MainViewModel
    private lateinit var viewModel: NewRequestViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_new_request, container, false)
        val myApp = requireActivity().application as MyApp
        mainViewModel = myApp.mainViewModel
        val title: EditText = rootView.findViewById(R.id.name_of_request)
        val text: EditText = rootView.findViewById(R.id.text_of_request)
        val createRequestButton: Button = rootView.findViewById(R.id.btn_create)
        val cancelRequestButton: Button = rootView.findViewById(R.id.btn_cancel)
        val categorySpinner: Spinner = rootView.findViewById(R.id.category_of_request)
        val categories = RequestCategory.values()
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_dropdown_item, categories)
        adapter.setDropDownViewResource(R.layout.custom_spinner_item)
        categorySpinner.adapter = adapter

        createRequestButton.setOnClickListener {
            val token = TokenManager.getToken(requireContext())
            if (token.isNullOrEmpty()) {
                Toast.makeText(
                    requireContext(),
                    "Токен пользователя отсутствует",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val apiService = RetrofitClient.createService(ApiService::class.java)
            val selectedCategory = categorySpinner.selectedItem as RequestCategory
            val formatter = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)
            val newRequest: RequestData = RequestData(
                32,
                title.text.toString(),
                date = formatter.format(LocalDateTime.now()),
                status = RequestStatus.NEW,
                category = selectedCategory,
                description = text.text.toString(),
                userToken = token
            )
            val call = apiService.createRequest(newRequest)
            call.enqueue(object : retrofit2.Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "Запрос успешно создан",
                            Toast.LENGTH_SHORT
                        ).show()
                        ServerDataManager(mainViewModel, requireContext()).loadRequestsFromServer()
                        navigateToRequestsFragment()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Произошла ошибка при создании запроса",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    // Обработка ошибки
                }
            })

        }

        cancelRequestButton.setOnClickListener {
            navigateToRequestsFragment()
        }
        return rootView


    }

    private fun navigateToRequestsFragment() {
        val activity = requireActivity() as? NavigationActivity
        activity?.openRequestsFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewRequestViewModel::class.java)
        // TODO: Use the ViewModel
    }

}