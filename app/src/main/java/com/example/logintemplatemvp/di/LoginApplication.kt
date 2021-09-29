package com.example.logintemplatemvp.di

import android.app.Application

class LoginApplication: Application() {
    val presenterContainer = PresenterContainer()
}