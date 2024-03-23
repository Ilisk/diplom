package com.example.diplom_jkh.data.request

enum class RequestCategory (val ruName: String){
    ELECTRICITY("Электричество"),
    GAS("Газ"),
    WATER("Вода");

    override fun toString(): String {
        return this.ruName
    }
}