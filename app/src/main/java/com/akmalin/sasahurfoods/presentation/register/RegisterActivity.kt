package com.akmalin.sasahurfoods.presentation.register

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.akmalin.sasahurfoods.R
import com.akmalin.sasahurfoods.data.datasource.auth.AuthDataSource
import com.akmalin.sasahurfoods.data.datasource.auth.FirebaseAuthDataSource
import com.akmalin.sasahurfoods.data.repository.UserRepository
import com.akmalin.sasahurfoods.data.repository.UserRepositoryImpl
import com.akmalin.sasahurfoods.data.source.network.firebase.FirebaseService
import com.akmalin.sasahurfoods.data.source.network.firebase.FirebaseServiceImpl
import com.akmalin.sasahurfoods.databinding.ActivityRegisterBinding
import com.akmalin.sasahurfoods.presentation.login.LoginActivity
import com.akmalin.sasahurfoods.presentation.main.MainActivity
import com.akmalin.sasahurfoods.utils.GenericViewModelFactory
import com.akmalin.sasahurfoods.utils.proceedWhen
import com.google.android.material.textfield.TextInputLayout

class RegisterActivity : AppCompatActivity(){
    private val binding: ActivityRegisterBinding by lazy {
        ActivityRegisterBinding.inflate(layoutInflater)
    }

    private val viewModel: RegisterViewModel by viewModels {
        val s: FirebaseService = FirebaseServiceImpl()
        val ds: AuthDataSource = FirebaseAuthDataSource(s)
        val r: UserRepository = UserRepositoryImpl(ds)
        GenericViewModelFactory.create(RegisterViewModel(r))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupForm()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.layoutForm.btnRegister.setOnClickListener {
            doRegister()
        }
        binding.layoutForm.tvNavToLogin.text = getString(R.string.text_already_has_account)
        binding.layoutForm.tvNavToLogin.setOnClickListener {
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java).apply {
              flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
          })
    }


    private fun doRegister() {
        if (isFormValid()) {
            val username = binding.layoutForm.etUsernameRegis.text.toString().trim()
            val password = binding.layoutForm.etPasswordRegis.text.toString().trim()
            val email = binding.layoutForm.etEmailRegis.text.toString().trim()
            val numberPhone = binding.layoutForm.etNumberPhoneRegis.text.toString().trim()
            proceedRegister(username, password, email, numberPhone)
        }
    }

    private fun proceedRegister(
        username: String,
        password: String,
        email: String,
        numberPhone: String
    ) {
        viewModel.doRegister(username, password, email, numberPhone).observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    binding.layoutForm.pbLoading.isVisible = false
                    binding.layoutForm.btnRegister.isVisible = true
                    navigateToMain()
                },
                doOnError = {
                    binding.layoutForm.pbLoading.isVisible = false
                    binding.layoutForm.btnRegister.isVisible = true
                    Toast.makeText(
                        this,
                        getString(R.string.login_failed),
                        Toast.LENGTH_SHORT
                    ).show()
                },
                doOnLoading = {
                    binding.layoutForm.pbLoading.isVisible = true
                    binding.layoutForm.btnRegister.isVisible = false
                    Toast.makeText(
                        this,
                        getString(R.string.loading),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            )
        }
    }

    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun isFormValid(): Boolean {
        val username = binding.layoutForm.etUsernameRegis.text.toString().trim()
        val password = binding.layoutForm.etPasswordRegis.text.toString().trim()
        val confirmPassword = binding.layoutForm.etConfirmPasswordRegis.text.toString().trim()
        val email = binding.layoutForm.etEmailRegis.text.toString().trim()
        val numberPhone = binding.layoutForm.etNumberPhoneRegis.text.toString().trim()

        return checkNameValidation(username) && checkPasswordValidation(
            password,
            binding.layoutForm.tilPasswordRegis
        ) &&
                checkEmailValidation(email) && checkPasswordValidation(
            confirmPassword,
            binding.layoutForm.tilConfirmPasswordRegis
        ) &&
                checkPwdAndConfirmPwd(password, confirmPassword)
    }

    private fun checkPwdAndConfirmPwd(password: String, confirmPassword: String): Boolean {
        return if (password != confirmPassword) {
            binding.layoutForm.tilPasswordRegis.isErrorEnabled = true
            binding.layoutForm.tilPasswordRegis.error =
                getString(R.string.text_password_does_not_match)
            binding.layoutForm.tilConfirmPasswordRegis.isErrorEnabled = true
            binding.layoutForm.tilConfirmPasswordRegis.error =
                getString(R.string.text_password_does_not_match)
            false
        } else {
            binding.layoutForm.tilPasswordRegis.isErrorEnabled = false
            binding.layoutForm.tilConfirmPasswordRegis.isErrorEnabled = false
            true
        }
    }

    private fun checkEmailValidation(email: String): Boolean {
        return if (email.isEmpty()) {
            binding.layoutForm.tilEmailRegis.isErrorEnabled = true
            binding.layoutForm.tilEmailRegis.error = getString(R.string.text_error_email_empty)
            false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.layoutForm.tilEmailRegis.isErrorEnabled = true
            binding.layoutForm.tilEmailRegis.error = getString(R.string.text_error_email_invalid)
            false
        } else {
            binding.layoutForm.tilEmailRegis.isErrorEnabled = false
            true
        }
    }

    private fun checkPasswordValidation(
        confirmPassword: String,
        textInputLayout: TextInputLayout
    ): Boolean {
        return if (confirmPassword.isEmpty()) {
            textInputLayout.isErrorEnabled = true
            textInputLayout.error =
                getString(R.string.text_error_password_empty)
            false
        } else if (confirmPassword.length < 8) {
            textInputLayout.isErrorEnabled = true
            textInputLayout.error =
                getString(R.string.text_error_password_less_than_8_char)
            false
        } else {
            textInputLayout.isErrorEnabled = false
            true
        }
    }

    private fun checkNameValidation(username: String): Boolean {
        return if (username.isEmpty()) {
            binding.layoutForm.tilUsernameRegis.isErrorEnabled = true
            binding.layoutForm.tilUsernameRegis.error =
                getString(R.string.text_error_name_cannot_empty)
            false
        } else {
            binding.layoutForm.tilUsernameRegis.isErrorEnabled = false
            true
        }
    }

    private fun setupForm() {
        with(binding.layoutForm) {
            tilUsernameRegis.isVisible = true
            tilPasswordRegis.isVisible = true
            tilEmailRegis.isVisible = true
            tilConfirmPasswordRegis.isVisible = true
        }
    }
}