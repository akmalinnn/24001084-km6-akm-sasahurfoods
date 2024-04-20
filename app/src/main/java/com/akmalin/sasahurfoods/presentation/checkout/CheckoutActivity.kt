package com.akmalin.sasahurfoods.presentation.checkout

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.akmalin.sasahurfoods.R
import com.akmalin.sasahurfoods.data.datasource.auth.AuthDataSource
import com.akmalin.sasahurfoods.data.datasource.auth.FirebaseAuthDataSource
import com.akmalin.sasahurfoods.data.datasource.cart.CartDataSource
import com.akmalin.sasahurfoods.data.datasource.menu.MenuDataSource
import com.akmalin.sasahurfoods.data.datasource.menu.MenuApiDataSource
import com.akmalin.sasahurfoods.data.datasource.cart.CartDatabaseDataSource
import com.akmalin.sasahurfoods.data.repository.CartRepository
import com.akmalin.sasahurfoods.data.repository.CartRepositoryImpl
import com.akmalin.sasahurfoods.data.repository.MenuRepository
import com.akmalin.sasahurfoods.data.repository.MenuRepositoryImpl
import com.akmalin.sasahurfoods.data.repository.UserRepository
import com.akmalin.sasahurfoods.data.repository.UserRepositoryImpl
import com.akmalin.sasahurfoods.data.source.local.database.AppDatabase
import com.akmalin.sasahurfoods.data.source.network.firebase.FirebaseService
import com.akmalin.sasahurfoods.data.source.network.firebase.FirebaseServiceImpl
import com.akmalin.sasahurfoods.data.source.network.services.FoodAppApiService
import com.akmalin.sasahurfoods.databinding.ActivityCheckoutBinding
import com.akmalin.sasahurfoods.presentation.checkout.adapter.PriceListAdapter
import com.akmalin.sasahurfoods.presentation.common.adapter.CartListAdapter
import com.akmalin.sasahurfoods.presentation.login.LoginActivity
import com.akmalin.sasahurfoods.presentation.main.MainActivity
import com.akmalin.sasahurfoods.utils.GenericViewModelFactory
import com.akmalin.sasahurfoods.utils.proceedWhen
import com.akmalin.sasahurfoods.utils.toIndonesianFormat


class CheckoutActivity : AppCompatActivity() {

    private val binding: ActivityCheckoutBinding by lazy {
        ActivityCheckoutBinding.inflate(layoutInflater)
    }

    private val viewModel: CheckoutViewModel by viewModels {
        val service: FirebaseService = FirebaseServiceImpl()
        val firebaseDataSource: AuthDataSource = FirebaseAuthDataSource(service)
        val firebaseRepository: UserRepository = UserRepositoryImpl(firebaseDataSource)
        val database = AppDatabase.getInstance(this)
        val dataSource: CartDataSource = CartDatabaseDataSource(database.cartDao())
        val cartRepository: CartRepository = CartRepositoryImpl(dataSource)
        val apiService = FoodAppApiService.invoke()
        val menuDataSource: MenuDataSource = MenuApiDataSource(apiService)
        val menuRepository: MenuRepository = MenuRepositoryImpl(menuDataSource)
        GenericViewModelFactory.create(CheckoutViewModel(cartRepository, firebaseRepository, menuRepository ))
    }


    private val adapter: CartListAdapter by lazy {
        CartListAdapter()
    }
    private val priceItemAdapter: PriceListAdapter by lazy {
        PriceListAdapter {

        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupList()
        observeData()
        setClickListeners()
    }

    private fun setClickListeners() {
        binding.ivBack.setOnClickListener {
            onBackPressed()
        }
        binding.btnCheckout.setOnClickListener {
            if (viewModel.isLoggedIn()) {
                doCheckout()
            } else {
                navigateToLogin()
            }
        }
    }

    private fun doCheckout() {
        viewModel.checkoutCart().observe(this){
            it.proceedWhen(
                doOnSuccess = {
                    displayCheckoutSuccessDialog()
                    viewModel.removeItemCart()
                },
                doOnError = {
                    Toast.makeText(this, getString(R.string.error), Toast.LENGTH_SHORT).show()
                }
            )
        }
    }

    private fun displayCheckoutSuccessDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_checkout_success, null)

        val dialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .create()

        dialogView.findViewById<Button>(R.id.btnFinish).setOnClickListener {
            dialog.dismiss()
            viewModel.removeItemCart()
            finish()
            navigateToMain()
        }
        dialog.show()
    }




    private fun navigateToMain() {
        startActivity(Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        })
    }

    private fun navigateToLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
    }

    private fun setupList() {
        binding.layoutContent.rvCart.adapter = adapter
        binding.layoutContent.rvShoppingSummary.adapter = priceItemAdapter
    }

    private fun observeData() {
        viewModel.checkoutData.observe(this) { result ->
            result.proceedWhen(
                doOnSuccess = {
                    binding.layoutState.root.isVisible = false
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = false
                    binding.layoutContent.root.isVisible = true
                    binding.layoutContent.rvCart.isVisible = true
                    binding.cvSectionOrder.isVisible = true
                    result.payload?.let { (carts, priceItems, totalPrice) ->
                        adapter.submitData(carts)
                        binding.tvTotalPrice.text = totalPrice.toIndonesianFormat()
                        priceItemAdapter.submitData(priceItems)

                    }
                }, doOnLoading = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = true
                    binding.layoutState.tvError.isVisible = false
                    binding.layoutContent.root.isVisible = false
                    binding.layoutContent.rvCart.isVisible = false
                    binding.cvSectionOrder.isVisible = false
                }, doOnError = {
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = result.exception?.message.orEmpty()
                    binding.layoutContent.root.isVisible = false
                    binding.layoutContent.rvCart.isVisible = false
                    binding.cvSectionOrder.isVisible = false
                }, doOnEmpty = { data ->
                    binding.layoutState.root.isVisible = true
                    binding.layoutState.pbLoading.isVisible = false
                    binding.layoutState.tvError.isVisible = true
                    binding.layoutState.tvError.text = getString(R.string.text_cart_is_empty)
                    data.payload?.let { (_, _, totalPrice) ->
                        binding.tvTotalPrice.text = totalPrice.toIndonesianFormat()
                    }
                    binding.layoutContent.root.isVisible = false
                    binding.layoutContent.rvCart.isVisible = false
                    binding.cvSectionOrder.isVisible = false
                })
        }
    }

}