package com.example.diplom_jkh.data.request

enum class RequestStatus(val ruName: String) {
    NEW("Создан"),
    IN_PROGRESS("В работе"),
    CLOSED("Выполнен");

    override fun toString(): String {
        return this.ruName
    }
}