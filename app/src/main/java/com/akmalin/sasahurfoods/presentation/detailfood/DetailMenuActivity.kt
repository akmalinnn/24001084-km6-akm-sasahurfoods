package com.akmalin.sasahurfoods.presentation.detailfood

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.akmalin.sasahurfoods.R
import com.akmalin.sasahurfoods.data.model.Menu
import com.akmalin.sasahurfoods.databinding.ActivityDetailMenuBinding
import com.akmalin.sasahurfoods.utils.proceedWhen
import com.akmalin.sasahurfoods.utils.toIndonesianFormat
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class DetailMenuActivity : AppCompatActivity() {
    lateinit var menu: Menu
    private val binding: ActivityDetailMenuBinding by lazy {
        ActivityDetailMenuBinding.inflate(layoutInflater)
    }

    private val viewModel: DetailMenuViewModel by viewModel {
        parametersOf(intent.extras)
    }

    companion object {
        const val EXTRAS_DETAIL_DATA = "EXTRAS_DETAIL_DATA"

        fun startActivity(
            context: Context,
            menu: Menu,
        ) {
            val intent = Intent(context, DetailMenuActivity::class.java)
            intent.putExtra(EXTRAS_DETAIL_DATA, menu)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        getIntentData()
        setClickListener()
        observeData()
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

    private fun setClickListener() {
        binding.menuDetail.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.menuCart.btnMinus.setOnClickListener {
            viewModel.minus()
        }
        binding.menuCart.btnPlus.setOnClickListener {
            viewModel.add()
        }
        binding.menuCart.btnAddToCart.setOnClickListener {
            addProductToCart()
        }
    }

    private fun addProductToCart() {
        viewModel.addToCart().observe(this) {
            it.proceedWhen(
                doOnSuccess = {
                    Toast.makeText(
                        this,
                        getString(R.string.text_add_to_cart_success),
                        Toast.LENGTH_SHORT,
                    ).show()
                    finish()
                },
                doOnError = {
                    Toast.makeText(
                        this,
                        getString(R.string.add_to_cart_failed),
                        Toast.LENGTH_SHORT,
                    ).show()
                    finish()
                },
                doOnLoading = {
                    Toast.makeText(
                        this,
                        getString(R.string.loading),
                        Toast.LENGTH_SHORT,
                    ).show()
                    binding.menuCart.btnAddToCart.isEnabled = false
                },
            )
        }
    }

    private fun observeData() {
        viewModel.priceLiveData.observe(this) { price ->
            binding.menuCart.btnAddToCart.isEnabled = price != 0.0
            if (price != 0.0) {
                binding.menuCart.btnAddToCart.text =
                    getString(R.string.detail_total_price, price.toIndonesianFormat())
            } else {
                binding.menuCart.btnAddToCart.text = getString(R.string.add_item)
            }
        }

        viewModel.menuCountLiveData.observe(this) {
            binding.menuCart.tvCount.text = it.toString()
        }
    }
}
