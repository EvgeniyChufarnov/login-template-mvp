package com.example.logintemplatemvp.domain.entities

data class UserEntity (
    val id: Int,
    val name: String,
    val token: String,
    val description: String
)