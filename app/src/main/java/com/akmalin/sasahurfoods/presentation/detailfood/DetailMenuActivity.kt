package com.akmalin.sasahurfoods.presentation.detailfood

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import coil.load
import com.akmalin.sasahurfoods.R
import com.akmalin.sasahurfoods.data.datasource.cart.CartDataSource
import com.akmalin.sasahurfoods.data.datasource.cart.CartDatabaseDataSource
import com.akmalin.sasahurfoods.data.model.Menu
import com.akmalin.sasahurfoods.data.repository.CartRepository
import com.akmalin.sasahurfoods.data.repository.CartRepositoryImpl
import com.akmalin.sasahurfoods.data.source.local.database.AppDatabase
import com.akmalin.sasahurfoods.databinding.ActivityDetailMenuBinding
import com.akmalin.sasahurfoods.utils.GenericViewModelFactory
import com.akmalin.sasahurfoods.utils.proceedWhen
import com.akmalin.sasahurfoods.utils.toIndonesianFormat


class DetailMenuActivity : AppCompatActivity() {

    lateinit var menu: Menu
    private val binding: ActivityDetailMenuBinding by lazy {
        ActivityDetailMenuBinding.inflate(layoutInflater)
    }

    private val viewModel: DetailMenuViewModel by viewModels {
        val db = AppDatabase.getInstance(this)
        val ds: CartDataSource = CartDatabaseDataSource(db.cartDao())
        val rp: CartRepository = CartRepositoryImpl(ds)
        GenericViewModelFactory.create(DetailMenuViewModel(intent?.extras, rp))
    }


    companion object {
        const val EXTRAS_DETAIL_DATA = "EXTRAS_DETAIL_DATA"
        fun startActivity(context: Context, menu: Menu) {
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
                    showToast(getString(R.string.text_add_to_cart_success))
                    finish()
                },
                doOnError = {
                    showToast(getString(R.string.add_to_cart_failed))
                },
                doOnLoading = {
                    showToast(getString(R.string.loading))
                    binding.menuCart.btnAddToCart.isEnabled = false
                }
            )
        }
    }

    private fun showToast(message: String) {
        val inflater = layoutInflater
        val layout: View = inflater.inflate(R.layout.layout_toast_custom, null)
        val textView: TextView = layout.findViewById(R.id.textViewToast)
        textView.text = message

        with(Toast(applicationContext)) {
            duration = Toast.LENGTH_SHORT
            view = layout
            show()
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
