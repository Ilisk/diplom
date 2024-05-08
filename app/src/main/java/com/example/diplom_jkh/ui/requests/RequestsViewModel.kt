package com.example.diplom_jkh.ui.requests

import androidx.lifecycle.ViewModel
import com.example.diplom_jkh.data.request.RequestData

// В RequestsViewModel
class RequestsViewModel : ViewModel() {
    private var _selectedRequestData: RequestData? = null
    val selectedRequestData: RequestData?
        get() = _selectedRequestData

    private var _selectedRequestNumber: Int? = null
    val selectedRequestNumber: Int?
        get() = _selectedRequestNumber

    // Метод для установки выбранного запроса и его позиции
    fun setSelectedRequest(requestData: RequestData, position: Int) {
        _selectedRequestData = requestData
        _selectedRequestNumber = position
    }

    fun getSelectedRequestData(): Pair<RequestData?, Int?> {
        return Pair(selectedRequestData, selectedRequestNumber)
    }

}
