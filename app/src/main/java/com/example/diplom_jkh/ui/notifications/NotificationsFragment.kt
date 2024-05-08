package com.example.diplom_jkh.ui.notifications

import NotificationComparator
import androidx.lifecycle.ViewModelProvider
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom_jkh.R
import com.example.diplom_jkh.data.MainViewModel
import com.example.diplom_jkh.data.MyApp
import com.example.diplom_jkh.data.notifications.NotificationData
import com.example.diplom_jkh.data.notifications.NotificationSpinnerItems
import com.example.diplom_jkh.data.request.RequestData
import com.example.diplom_jkh.data.request.RequestSpinnerItems
import com.example.diplom_jkh.data.request.RequestStatus
import com.example.diplom_jkh.http.ServerDataManager
import com.example.diplom_jkh.ui.requests.RequestsAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationsFragment : Fragment() {
    private lateinit var adapter: NotificationsAdapter
    private var notificationsList = mutableListOf<NotificationData>()
    private lateinit var mainViewModel: MainViewModel

    companion object {
        fun newInstance() = NotificationsFragment()
    }

    private lateinit var viewModel: NotificationsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val myApp = requireActivity().application as MyApp
        mainViewModel = myApp.mainViewModel
        val rootView = inflater.inflate(R.layout.fragment_notifications, container, false)
        val recyclerView: RecyclerView = rootView.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = NotificationsAdapter(notificationsList)
        recyclerView.adapter = adapter
        val sortSpinner: Spinner = rootView.findViewById(R.id.sortSpinner)
        val sortOptions = NotificationSpinnerItems.values()
        val spinnerAdapter =
            ArrayAdapter(requireContext(), R.layout.spinner_dropdown_item, sortOptions)
        spinnerAdapter.setDropDownViewResource(R.layout.custom_spinner_item)
        sortSpinner.adapter = spinnerAdapter
        ServerDataManager(mainViewModel, requireContext()).loadNotificationsFromServer()
        val comparator = NotificationComparator()
        sortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                when (sortOptions[position]) {
                    NotificationSpinnerItems.CURRENT -> {
                        val currentNotifications = notificationsList.filter {
                            it.dateOfStart.isNotEmpty() && it.dateOfEnd.isEmpty()
                        }
                        currentNotifications.sortedWith(comparator)
                        adapter.updateList(currentNotifications)
                    }
                    NotificationSpinnerItems.NEW -> {
                        val newNotifications = notificationsList.filter {
                            it.dateOfStart.isEmpty() && it.dateOfEnd.isEmpty()
                        }
                        newNotifications.sortedWith(comparator)
                        adapter.updateList(newNotifications)
                    }
                    NotificationSpinnerItems.OLD -> {
                        val archivedNotifications = notificationsList.filter {
                            it.dateOfEnd.isNotEmpty()
                        }
                        archivedNotifications.sortedWith(comparator)
                        adapter.updateList(archivedNotifications)
                    }
                    NotificationSpinnerItems.ALL -> {
                        notificationsList.sortedWith(comparator)
                        adapter.updateList(notificationsList)
                    }
                }
                //notificationsList.sortedWith(comparator)
                //adapter.updateList(notificationsList)

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Обработка события, когда ничего не выбрано
            }
        }
        CoroutineScope(Dispatchers.Main).launch {
            loadNotifications()
        }
        return rootView
    }
    private fun loadNotifications() {
        mainViewModel.notificationsList?.let { notifications ->
            notificationsList.addAll(notifications)
            adapter.notifyDataSetChanged()
        } ?: run {
            Toast.makeText(requireContext(), "Список запросов пуст", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}