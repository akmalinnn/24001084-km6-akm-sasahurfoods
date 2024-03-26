package com.akmalin.sasahurfoods.presentation.detailfood

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.akmalin.sasahurfoods.R
import com.akmalin.sasahurfoods.data.model.Menu
import com.akmalin.sasahurfoods.databinding.ActivityDetailFoodBinding
import com.akmalin.sasahurfoods.utils.GenericViewModelFactory
import com.akmalin.sasahurfoods.utils.toIndonesianFormat

class DetailFoodActivity : AppCompatActivity() {
    private var quantity: Int = 0
    private lateinit var menu: Menu

    private val viewModel: DetailFoodViewModel by viewModels {
        GenericViewModelFactory.create(DetailFoodViewModel(intent?.extras))
    }


    companion object {
        const val EXTRAS_DETAIL_DATA = "EXTRAS_DETAIL_DATA"
        fun startActivity(context: Context, menu: Menu) {
            val intent = Intent(context, DetailFoodActivity::class.java)
            intent.putExtra(EXTRAS_DETAIL_DATA, menu)
            context.startActivity(intent)
        }
    }

    private val binding: ActivityDetailFoodBinding by lazy {
        ActivityDetailFoodBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getIntentData()
        setupQuantityButtons()

        binding.menuDetail.ivBack.setOnClickListener {
            onBackPressed()
        }
    }

    private fun getIntentData() {
        menu = intent.getParcelableExtra(EXTRAS_DETAIL_DATA) ?: return
        binding.menuDetail.ivMenuImage.load(menu.imgUrl)
        binding.menuDetail.tvDesc.text = menu.desc
        binding.menuDetail.tvMenuName.text = menu.name
        binding.menuDetail.tvMenuPrice.text = menu.price.toIndonesianFormat()
        binding.menuLocation.tvMenuLocation.text = menu.location

        binding.menuLocation.tvMenuLocation.setOnClickListener {
            val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(menu.locUrl))
            startActivity(mapIntent)
        }
    }

    private fun setupQuantityButtons() {
        binding.menuCart.btnMinus.setOnClickListener {
            if (quantity > 0) {
                quantity--
                updateQuantityAndTotalPrice()
            }
        }

        binding.menuCart.btnPlus.setOnClickListener {
            quantity++
            updateQuantityAndTotalPrice()
        }
    }

    private fun updateQuantityAndTotalPrice() {
        binding.menuCart.tvCount.text = quantity.toString()
        val menu = intent.getParcelableExtra<Menu>(EXTRAS_DETAIL_DATA)
        val totalPrice = menu?.price?.times(quantity) ?: 0.0

        if (quantity == 0) {
            binding.menuCart.btnCheckout.text = getString(R.string.checkout)
        } else {
            "Total: ${totalPrice.toIndonesianFormat()}".also {
                binding.menuCart.btnCheckout.text = it }
        }
    }
}
