package com.example.logintemplatemvp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.logintemplatemvp.R
import com.example.logintemplatemvp.databinding.ActivityMainBinding
import com.example.logintemplatemvp.ui.login.LoginFragment

class MainActivity : AppCompatActivity(R.layout.activity_main), LoginFragment.Contract {
    private val binding: ActivityMainBinding by viewBinding(ActivityMainBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            navigateTo(LoginFragment())
        }
    }

    override fun navigateToSignUpScreen() {
        // navigation to SignUpFragment
    }

    override fun navigateToForgotPasswordScreen() {
        // navigation to ForgotPasswordFragment
    }

    override fun navigateOnSuccessfulLogin() {
        // navigation to whatever fragment
    }

    private fun navigateTo(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(binding.containerLayout.id, fragment)
            .commit()
    }
}