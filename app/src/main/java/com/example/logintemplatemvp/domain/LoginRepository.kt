package com.example.logintemplatemvp.domain

import com.example.logintemplatemvp.domain.entities.UserEntity

interface LoginRepository {
    suspend fun tryToLogin(email: String, password: String): ResultWrapper<UserEntity>
}