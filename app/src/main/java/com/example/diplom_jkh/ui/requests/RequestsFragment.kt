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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom_jkh.NavigationActivity
import com.example.diplom_jkh.R
import com.example.diplom_jkh.data.NewRequestClickListener
import com.example.diplom_jkh.data.request.RequestCategory
import com.example.diplom_jkh.data.request.RequestData
import com.example.diplom_jkh.data.request.RequestStatus
import com.example.diplom_jkh.data.request.RequestSpinnerItems
import com.example.diplom_jkh.ui.RequestsAdapter
import com.example.diplom_jkh.ui.profile.ProfileFragment

class RequestsFragment : Fragment() {
    private val profileFragment = ProfileFragment.newInstance()
    private val requestsList = mutableListOf(
        RequestData(
            1,
            "Нет горячей воды",
            "10 марта 2024",
            RequestStatus.IN_PROGRESS,
            RequestCategory.WATER,
            "уже 4 дня нет горячей воды",
            profileFragment.getPerson()
        ),
        RequestData(
            2,
            "Нет света два дня",
            "15 марта 2024",
            RequestStatus.NEW,
            RequestCategory.ELECTRICITY,
            "после грозы выбило пробки",
            profileFragment.getPerson()
        ),
        RequestData(
            3,
            "Уменьшите цены на газ",
            "20 марта 2024",
            RequestStatus.CLOSED,
            RequestCategory.GAS,
            "слишком дорого выходит за месяц!",
            profileFragment.getPerson()
        )

    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_requests, container, false)
        val addRequestButton: ImageButton = rootView.findViewById(R.id.addRequestButton)
        val recyclerView: RecyclerView = rootView.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = RequestsAdapter(requestsList)
        recyclerView.adapter = adapter
        val sortSpinner: Spinner = rootView.findViewById(R.id.sortSpinner)
        val sortOptions = RequestSpinnerItems.values()
        val spinnerAdapter =
            ArrayAdapter(requireContext(), R.layout.spinner_dropdown_item, sortOptions)
        spinnerAdapter.setDropDownViewResource(R.layout.custom_spinner_item)
        sortSpinner.adapter = spinnerAdapter

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
        fun getRequestsList(): List<RequestData> {
            return this.requestsList
        }
        addRequestButton.setOnClickListener {
            // Получить активность и вызвать метод из активности
            val activity = requireActivity() as? NavigationActivity
            activity?.openNewRequestsFragment()
        }
        return rootView
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Здесь можно инициализировать ViewModel, если это необходимо
    }


}
