package com.example.diplom_jkh.ui.requests

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.diplom_jkh.R
import com.example.diplom_jkh.data.request.RequestData
import android.util.Log
import com.example.diplom_jkh.data.request.RequestCategory

class RequestsAdapter(private var requestsList: List<RequestData>, private val itemClickListener: RequestItemClickListener) :
    RecyclerView.Adapter<RequestsAdapter.RequestViewHolder>()  {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.request_item, parent, false)
        return RequestViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val currentRequest = requestsList[position]
        holder.titleTextView.text = currentRequest.title
        holder.dateTextView.text = "Дата: ${currentRequest.date}"
        holder.statusTextView.text = "Статус: ${currentRequest.status.ruName}"
        holder.imageView.setImageResource(getIconResId(currentRequest.category))

        Log.d(
            "RequestsAdapter",
            "IconResId for item at position $position: ${currentRequest.iconResId}"
        )

        holder.itemView.setOnClickListener {
            itemClickListener.onItemClick(currentRequest)
        }
    }

    private fun getIconResId(category: RequestCategory): Int {
        return when (category) {
            RequestCategory.GAS -> R.drawable.gas
            RequestCategory.WATER -> R.drawable.water
            RequestCategory.ELECTRICITY -> R.drawable.electricity
        }
    }


    fun updateList(newList: List<RequestData>) {
        requestsList = newList
        notifyDataSetChanged()
    }

    override fun getItemCount() = requestsList.size

    inner class RequestViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val statusTextView: TextView = itemView.findViewById(R.id.statusTextView)
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }
}
