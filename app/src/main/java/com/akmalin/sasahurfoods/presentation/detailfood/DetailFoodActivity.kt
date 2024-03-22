package com.akmalin.sasahurfoods.presentation.detailfood

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.akmalin.sasahurfoods.R
import com.akmalin.sasahurfoods.databinding.ActivityCheckoutBinding
import com.akmalin.sasahurfoods.databinding.ActivityDetailFoodBinding

class DetailFoodActivity : AppCompatActivity() {
    private val binding : ActivityDetailFoodBinding by lazy {
        ActivityDetailFoodBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}