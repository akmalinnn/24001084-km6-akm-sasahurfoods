package com.akmalin.sasahurfoods.presentation.auth.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.akmalin.sasahurfoods.R
import com.akmalin.sasahurfoods.databinding.ActivityLoginBinding
import com.akmalin.sasahurfoods.presentation.auth.register.RegisterActivity
import com.akmalin.sasahurfoods.presentation.main.MainActivity
import com.akmalin.sasahurfoods.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity : AppCompatActivity() {
    private val binding: ActivityLoginBinding by lazy {
        ActivityLoginBinding.inflate(layoutInflater)
    }

    private val loginViewModel: LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setClickListener()
    }

    private fun setClickListener() {
        binding.layoutFormLogin.btnLogin.setOnClickListener {
            inputLogin()
        }
        binding.layoutFormLogin.tvNavToRegister.text = getString(R.string.text_nav_to_register)
        binding.layoutFormLogin.tvNavToRegister.setOnClickListener {
            navigateRegister()
        }
    }

    private fun inputLogin() {
        val email = binding.layoutFormLogin.etEmailLogin.text.toString().trim()
        val password = binding.layoutFormLogin.etPasswordLogin.text.toString().trim()

        if (isEmailAndPasswordNotEmpty(email, password)) {
            doLogin(email, password)
        } else {
            Toast.makeText(
                this,
                getString(R.string.login_failed),
                Toast.LENGTH_SHORT,
            ).show()
        }
    }

    private fun doLogin(
        email: String,
        password: String,
    ) {
        loginViewModel.doLogin(email, password).observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.layoutFormLogin.pbLogin.isVisible = false
                    binding.layoutFormLogin.pbLogin.isEnabled = true
                    Toast.makeText(
                        this,
                        getString(R.string.login_success),
                        Toast.LENGTH_SHORT,
                    ).show()
                    navigateToMain()
                },
                doOnLoading = {
                    binding.layoutFormLogin.pbLogin.isVisible = true
                    binding.layoutFormLogin.pbLogin.isEnabled = false
                },
                doOnError = {
                    binding.layoutFormLogin.pbLogin.isVisible = false
                    binding.layoutFormLogin.pbLogin.isEnabled = true
                    Toast.makeText(
                        this,
                        getString(R.string.login_failed),
                        Toast.LENGTH_SHORT,
                    ).show()
                },
            )
        }
    }

    private fun isEmailAndPasswordNotEmpty(
        email: String,
        password: String,
    ): Boolean {
        val isEmailValid = checkEmailValidation(email)
        val isPasswordValid = checkPasswordValidation(password)

        return isEmailValid && isPasswordValid
    }

    private fun checkEmailValidation(email: String): Boolean {
        return if (email.isEmpty()) {
            binding.layoutFormLogin.tilEmailLogin.isErrorEnabled = true
            binding.layoutFormLogin.tilEmailLogin.error = getString(R.string.text_error_email_empty)
            false
        } else {
            binding.layoutFormLogin.tilEmailLogin.isErrorEnabled = false
            true
        }
    }

    private fun checkPasswordValidation(password: String): Boolean {
        return if (password.isEmpty()) {
            binding.layoutFormLogin.tilPasswordLogin.isErrorEnabled = true
            binding.layoutFormLogin.tilPasswordLogin.error = getString(R.string.text_error_password_empty)
            false
        } else {
            binding.layoutFormLogin.tilPasswordLogin.isErrorEnabled = false
            true
        }
    }

    private fun navigateToMain() {
        startActivity(
            Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            },
        )
    }

    private fun navigateRegister() {
        startActivity(
            Intent(this, RegisterActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            },
        )
    }
}
