package com.akmalin.sasahurfoods.presentation.profile

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.akmalin.sasahurfoods.R
import com.akmalin.sasahurfoods.databinding.FragmentCartBinding
import com.akmalin.sasahurfoods.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Disable edit text fields initially
        binding.emailEdit.isEnabled = false
        binding.nameEdit.isEnabled = false
        binding.usernameEdit.isEnabled = false

        binding.btnEdit.setOnClickListener {
            val isEditing = !binding.emailEdit.isEnabled
            binding.emailEdit.isEnabled = isEditing
            binding.nameEdit.isEnabled = isEditing
            binding.usernameEdit.isEnabled = isEditing

            setButtonText(isEditing)
        }

        setButtonText(false)
    }

    private fun setButtonText(isEditing: Boolean) {
        binding.btnEdit.text = if (isEditing) {
            getString(R.string.save)
        } else {
            getString(R.string.edit)
        }
    }


}