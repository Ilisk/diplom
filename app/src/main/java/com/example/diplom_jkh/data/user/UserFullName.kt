package com.example.diplom_jkh.data.user

data class UserFullName(
    val surname: String,
    val name: String,
    val middleName: String?
)
{
    fun getFullName() : String {
        return "$surname $name $middleName"
    }
}
