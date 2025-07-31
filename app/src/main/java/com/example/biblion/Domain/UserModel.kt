package com.example.biblion.Domain


data class UserModel(
    val username: String = "",
    val password: String = "",
    val email: String = "",
    val favorites: List<String> = emptyList()
)