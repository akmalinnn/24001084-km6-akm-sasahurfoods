package com.akmalin.sasahurfoods.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import coil.load
import coil.transform.CircleCropTransformation
import com.akmalin.sasahurfoods.R
import com.akmalin.sasahurfoods.databinding.FragmentCartBinding
import com.akmalin.sasahurfoods.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding
    private val viewModel: ProfileViewModel by viewModels()

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
    }

    private fun setButtonText() {
        binding.btnEdit.text = if (viewModel.isEditMode.value == false) {
            getString(R.string.save)
        } else {
            getString(R.string.edit)
        }
    }

    private fun observeEditMode() {
        viewModel.isEditMode.observe(viewLifecycleOwner) {
            binding.emailEdit.isEnabled = it
            binding.nameEdit.isEnabled = it
            binding.usernameEdit.isEnabled = it
        }
    }



}