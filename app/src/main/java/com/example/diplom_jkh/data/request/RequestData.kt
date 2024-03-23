package com.example.diplom_jkh.data.request
import androidx.annotation.DrawableRes
import com.example.diplom_jkh.R
import com.example.diplom_jkh.data.user.UserData
import java.time.LocalDateTime

data class RequestData(
    val id: Int,
    val title: String,
    val date: String = LocalDateTime.now().toString(),
    val status: RequestStatus,
    val category: RequestCategory,
    val description: String,
    val user: UserData,
    val imageUrl: String? = null,
    @DrawableRes
    val iconResId: Int = when (category) {
        RequestCategory.GAS -> R.drawable.gas
        RequestCategory.WATER -> R.drawable.water
        RequestCategory.ELECTRICITY -> R.drawable.electricity
    }
)