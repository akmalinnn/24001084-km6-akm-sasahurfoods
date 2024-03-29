package com.akmalin.sasahurfoods.presentation.profile

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.akmalin.sasahurfoods.data.model.Profile

class ProfileViewModel : ViewModel(){
    val profileData = MutableLiveData(
        Profile.Profile(
            name = "Akmal Nafis",
            username = "akmalinnnn",
            email = "akmalinnn@gmail.com",
            profileImg = "https://avatars.githubusercontent.com/u/22763869?v=4"
        )
    )

    val isEditMode = MutableLiveData(false)

    fun changeEditMode() {
        val currentValue = isEditMode.value ?: false
        isEditMode.postValue(!currentValue)
    }
}