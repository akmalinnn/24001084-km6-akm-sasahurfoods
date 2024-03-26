package com.akmalin.sasahurfoods.presentation.detailfood

import android.os.Bundle
import androidx.lifecycle.ViewModel
import com.akmalin.sasahurfoods.data.model.Menu

class DetailFoodViewModel(private val extras: Bundle?) : ViewModel(){
    val menu = extras?.getParcelable<Menu>(DetailFoodActivity.EXTRAS_DETAIL_DATA)
}