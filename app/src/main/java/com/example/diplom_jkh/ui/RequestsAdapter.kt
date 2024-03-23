package com.example.diplom_jkh.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom_jkh.R
import com.example.diplom_jkh.data.request.RequestData

class RequestsAdapter(private var requests: List<RequestData>) : RecyclerView.Adapter<RequestsAdapter.RequestViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.request_item, parent, false)
        return RequestViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val currentRequest = requests[position]
        holder.titleTextView.text = currentRequest.title
        holder.dateTextView.text = "Дата: ${currentRequest.date}"
        holder.statusTextView.text = "Статус: ${currentRequest.status.ruName}"
        holder.imageView.setImageResource(currentRequest.iconResId)
    }
    fun updateList(newList: List<RequestData>) {
        requests = newList
        notifyDataSetChanged()
    }

    override fun getItemCount() = requests.size

    inner class RequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val dateTextView : TextView = itemView.findViewById(R.id.dateTextView)
        val statusTextView : TextView = itemView.findViewById(R.id.statusTextView)
        val detailedView: TextView = itemView.findViewById(R.id.detailedView)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    // Метод для сортировки списка по статусу запроса
//    fun filterByStatus(status: String) {
//        filteredRequests = requests.filter { it.status == status } // Оставляем только элементы с заданным статусом
//        notifyDataSetChanged() // Обновляем список в RecyclerView
//    }
}