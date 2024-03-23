package com.example.diplom_jkh.data.user

data class UserAddress(
    val city: String,
    val district: String?,
    val street: String,
    val building: Int,
    val apartmentNumber: Int

) {
    fun getFullAddress(): String {
        var address = "г. $city"
        if (district != null) address += ", $district район"
        address += ", д. $building, кв. $apartmentNumber"
        return address

    }
}
