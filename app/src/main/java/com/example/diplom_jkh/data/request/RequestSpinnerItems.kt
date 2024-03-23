package com.example.diplom_jkh.data.request

enum class RequestSpinnerItems(val ruName : String) {
    ALL("Все"),
    CURRENT("Текущие"),
    OLD("Архивные");

    override fun toString(): String {
        return this.ruName
    }
}