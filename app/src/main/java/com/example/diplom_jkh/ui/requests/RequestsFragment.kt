package com.example.diplom_jkh.ui.requests

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom.http.RetrofitClient
import com.example.diplom_jkh.NavigationActivity
import com.example.diplom_jkh.R
import com.example.diplom_jkh.data.MainViewModel
import com.example.diplom_jkh.data.MyApp
import com.example.diplom_jkh.data.request.RequestData
import com.example.diplom_jkh.data.request.RequestStatus
import com.example.diplom_jkh.data.request.RequestSpinnerItems
import com.example.diplom_jkh.http.ApiService
import com.example.diplom_jkh.http.ServerDataManager
import com.example.diplom_jkh.http.ServerResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RequestsFragment : Fragment(),  RequestItemClickListener {
    //val requestsViewModel: RequestsViewModel by viewModels()
    private lateinit var adapter: RequestsAdapter
    private var requestsList = mutableListOf<RequestData>()
    private lateinit var mainViewModel: MainViewModel

    companion object {
        fun newInstance() = RequestsFragment()
    }

    override fun onItemClick(requestData: RequestData) {
        val position = requestsList.indexOf(requestData)
        mainViewModel.setSelectedRequest(requestData, position)
        val activity = requireActivity() as? NavigationActivity
        activity?.openDetailedFragment()
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myApp = requireActivity().application as MyApp
        mainViewModel = myApp.mainViewModel
        val rootView = inflater.inflate(R.layout.fragment_requests, container, false)
        val addRequestButton: ImageButton = rootView.findViewById(R.id.addRequestButton)
        val recyclerView: RecyclerView = rootView.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = RequestsAdapter(requestsList, this)
        recyclerView.adapter = adapter
        val sortSpinner: Spinner = rootView.findViewById(R.id.sortSpinner)
        val sortOptions = RequestSpinnerItems.values()
        val spinnerAdapter =
            ArrayAdapter(requireContext(), R.layout.spinner_dropdown_item, sortOptions)
        spinnerAdapter.setDropDownViewResource(R.layout.custom_spinner_item)
        sortSpinner.adapter = spinnerAdapter
        ServerDataManager(mainViewModel, requireContext()).loadRequestsFromServer()


        sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {

                when (sortOptions[position]) {

                    RequestSpinnerItems.CURRENT -> {
                        val filteredList =
                            requestsList.filter { it.status == RequestStatus.IN_PROGRESS }
                        adapter.updateList(filteredList)
                    }

                    RequestSpinnerItems.NEW -> {
                        val filteredList = requestsList.filter { it.status == RequestStatus.NEW }
                        adapter.updateList(filteredList)
                    }

                    RequestSpinnerItems.OLD -> {
                        val filteredList = requestsList.filter { it.status == RequestStatus.CLOSED }
                        adapter.updateList(filteredList)
                    }

                    RequestSpinnerItems.ALL -> {
                        adapter.updateList(requestsList)
                    }
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Обработка события, когда ничего не выбрано
            }
        }
        CoroutineScope(Dispatchers.Main).launch {
            loadRequests()
        }

        addRequestButton.setOnClickListener {
            // Получить активность и вызвать метод из активности
            val activity = requireActivity() as? NavigationActivity
            activity?.openNewRequestsFragment()
        }

        return rootView
    }
    private fun loadRequests() {
        mainViewModel.requestsList?.let { requests ->
            requestsList.addAll(requests)
            adapter.notifyDataSetChanged()
        } ?: run {
            Toast.makeText(requireContext(), "Список запросов пуст", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Здесь можно инициализировать ViewModel, если это необходимо
    }


}