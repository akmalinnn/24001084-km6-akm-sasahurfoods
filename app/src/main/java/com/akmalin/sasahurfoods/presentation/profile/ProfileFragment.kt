package com.akmalin.sasahurfoods.presentation.profile

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.CircleCropTransformation
import com.akmalin.sasahurfoods.R
import com.akmalin.sasahurfoods.data.datasource.auth.AuthDataSource
import com.akmalin.sasahurfoods.data.datasource.auth.FirebaseAuthDataSource
import com.akmalin.sasahurfoods.data.repository.UserRepository
import com.akmalin.sasahurfoods.data.repository.UserRepositoryImpl
import com.akmalin.sasahurfoods.data.source.network.firebase.FirebaseService
import com.akmalin.sasahurfoods.data.source.network.firebase.FirebaseServiceImpl
import com.akmalin.sasahurfoods.databinding.FragmentCartBinding
import com.akmalin.sasahurfoods.databinding.FragmentProfileBinding
import com.akmalin.sasahurfoods.presentation.login.LoginActivity
import com.akmalin.sasahurfoods.utils.GenericViewModelFactory

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels{
        val service = FirebaseServiceImpl()
        val dataSource: AuthDataSource = FirebaseAuthDataSource(service)
        val repository: UserRepository = UserRepositoryImpl(dataSource)
        GenericViewModelFactory.create(ProfileViewModel(repository))
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadProfileData()
        setClickListener()
        observeEditMode()
        observeLoginStatus()
    }


        private fun loadProfileData() {
            viewModel.profileData.observe(viewLifecycleOwner) {
                binding.ivProfileImage.load(it.profileImg) {
                    crossfade(true)
                    error(R.drawable.ic_tab_profile)
                    transformations(CircleCropTransformation())
                }
                binding.nameEdit.setText(it.name)
                binding.usernameEdit.setText(it.username)
                binding.emailEdit.setText(it.email)
            }

        }


    private fun setClickListener() {
        binding.btnEdit.setOnClickListener {
            viewModel.changeEditMode()
            setButtonText()
        }
        binding.btnLogout.setOnClickListener{
            viewModel.doLogout()
            navigateToLogin()
            val navController = findNavController()
            navController.navigate(R.id.menu_tab_home)
        }
    }

    private fun setButtonText() {
        binding.btnEdit.text = if (viewModel.isEditMode.value == false) {
            getString(R.string.save)
        } else {
            getString(R.string.edit)
        }
    }

    private fun observeEditMode() {
        viewModel.isEditMode.observe(viewLifecycleOwner){ isEditMode ->
            isEditMode?.let {
                binding.emailEdit.isEnabled = it
                binding.nameEdit.isEnabled = it
                binding.usernameEdit.isEnabled = it

            }
        }
    }

    private fun observeLoginStatus() {
        if (!viewModel.isLoggedIn()) {
            navigateToLogin()
        }
    }

    private fun navigateToLogin() {
        startActivity(Intent(requireContext(), LoginActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }
}

