package com.akmalin.sasahurfoods.presentation.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.akmalin.sasahurfoods.R
import com.akmalin.sasahurfoods.databinding.FragmentProfileBinding
import com.akmalin.sasahurfoods.presentation.auth.login.LoginActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    private val profileViewModel: ProfileViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        loadProfileData()
        setClickListener()
        observeEditMode()
        observeLoginStatus()
    }

    private fun loadProfileData() {
        profileViewModel.getCurrentUser()?.let {
            binding.etUsernameEdit.setText(it.username)
            binding.etEmailEdit.setText(it.email)
        }
    }

    private fun setClickListener() {
        binding.btnEdit.setOnClickListener {
            profileViewModel.changeEditMode()
            setButtonText()
        }
        binding.btnLogout.setOnClickListener {
            profileViewModel.doLogout()
            val navController = findNavController()
            navController.navigate(R.id.menu_tab_home)
            Toast.makeText(activity, getString(R.string.logout_success), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setButtonText() {
        binding.btnEdit.text =
            if (profileViewModel.isEditMode.value == false) {
                getString(R.string.save)
            } else {
                getString(R.string.edit)
            }
    }

    private fun observeEditMode() {
        profileViewModel.isEditMode.observe(viewLifecycleOwner) { isEditMode ->
            isEditMode?.let {
                binding.etEmailEdit.isEnabled = it
                binding.etUsernameEdit.isEnabled = it
            }
        }
    }

    private fun observeLoginStatus() {
        if (!profileViewModel.isLoggedIn()) {
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        startActivity(
            Intent(requireContext(), LoginActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            },
        )
    }
}
