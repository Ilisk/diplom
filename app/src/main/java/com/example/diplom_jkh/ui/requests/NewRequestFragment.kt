package com.example.diplom_jkh.ui.requests

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import com.example.diplom_jkh.R
import com.example.diplom_jkh.data.request.RequestCategory
import com.example.diplom_jkh.data.request.RequestData
import com.example.diplom_jkh.data.request.RequestStatus
import com.example.diplom_jkh.ui.profile.ProfileFragment

class NewRequestFragment : Fragment() {
    private val profileFragment = ProfileFragment.newInstance()
    companion object {
        fun newInstance() = NewRequestFragment()
    }

    private lateinit var viewModel: NewRequestViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_new_request, container, false)
        val createRequestButton: Button = rootView.findViewById(R.id.btn_create)
        val categorySpinner: Spinner = rootView.findViewById(R.id.category_of_request)
        val categories = RequestCategory.values()
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_dropdown_item, categories)
        adapter.setDropDownViewResource(R.layout.custom_spinner_item)
        categorySpinner.adapter = adapter

        return rootView

        createRequestButton.setOnClickListener {
            val newRequest: RequestData = RequestData(
                32,
                "aa",
                status = RequestStatus.NEW,
                category = RequestCategory.ELECTRICITY,
                description = "ada",
                user = profileFragment.getPerson()
            )

        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(NewRequestViewModel::class.java)
        // TODO: Use the ViewModel
    }

}