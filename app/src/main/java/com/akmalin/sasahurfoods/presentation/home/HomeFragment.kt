package com.akmalin.sasahurfoods.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import coil.load
import com.akmalin.sasahurfoods.R
import com.akmalin.sasahurfoods.data.model.Category
import com.akmalin.sasahurfoods.data.model.Menu
import com.akmalin.sasahurfoods.databinding.FragmentHomeBinding
import com.akmalin.sasahurfoods.presentation.detailfood.DetailMenuActivity
import com.akmalin.sasahurfoods.presentation.home.adapter.CategoryAdapter
import com.akmalin.sasahurfoods.presentation.home.adapter.MenuAdapter
import com.akmalin.sasahurfoods.utils.proceedWhen
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private val homeViewModel: HomeViewModel by viewModel()

    private val menuAdapter: MenuAdapter by lazy {
        MenuAdapter(homeViewModel.getListMode()) {
            navigateToDetail(it)
        }
    }

    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter {
            getMenuList(it.name)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?,
    ) {
        super.onViewCreated(view, savedInstanceState)
        loadProfileData()
        observeGridMode()
        initCategory()
        initMenu()
        getCategoryList()
        getMenuList()
        setClickActionMenu()
        bindBanner()
    }

    private fun initMenu() {
        binding.rvMenu.apply {
            adapter = menuAdapter
        }
    }

    private fun initCategory() {
        binding.rvCategory.apply {
            adapter = categoryAdapter
        }
    }

    private fun observeGridMode() {
        homeViewModel.isUsingGridMode.observe(viewLifecycleOwner) { isUsingGridMode ->
            setButtonImage(isUsingGridMode)
            changeLayoutMode(isUsingGridMode)
        }
    }

    private fun bindCategory(data: List<Category>) {
        categoryAdapter.submitData(data)
    }

    private fun getCategoryList() {
        homeViewModel.getCategories().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data -> bindCategory(data) }
                    binding.layoutHomeStateCategory.pbLoading.isVisible = false
                    binding.layoutHomeStateCategory.tvError.isVisible = false
                },
                doOnLoading = {
                    binding.layoutHomeStateCategory.pbLoading.isVisible = true
                    binding.layoutHomeStateCategory.tvError.isVisible = false
                },
                doOnError = {
                    binding.layoutHomeStateCategory.pbLoading.isVisible = false
                    binding.layoutHomeStateCategory.tvError.isVisible = true
                },
            )
        }
    }

    private fun changeLayoutMode(usingGridMode: Boolean) {
        val listMode = if (usingGridMode) MenuAdapter.MODE_GRID else MenuAdapter.MODE_LIST
        menuAdapter.updateLayout(listMode)
        binding.rvMenu.apply {
            layoutManager = GridLayoutManager(requireContext(), if (usingGridMode) 2 else 1)
        }
        initMenu()
    }

    private fun bindBanner() {
        binding.layoutHeader.layoutBanner.ivFoodBanner.load(getString(R.string.home_header_food))
        binding.layoutHeader.layoutBanner.ivIconBanner.load(getString(R.string.home_header_discount))
        binding.layoutHeader.layoutBanner.bgHomeBanner.load(getString(R.string.home_header_banner))
    }

    private fun getMenuList(name: String? = null) {
        homeViewModel.getMenus(name).observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data ->
                        bindMenuList(data)
                        binding.layoutHomeStateMenu.pbLoading.isVisible = false
                        binding.layoutHomeStateMenu.tvError.isVisible = false
                    }
                },
                doOnLoading = {
                    binding.layoutHomeStateMenu.pbLoading.isVisible = true
                    binding.layoutHomeStateMenu.tvError.isVisible = false
                },
                doOnError = {
                    binding.layoutHomeStateMenu.pbLoading.isVisible = false
                    binding.layoutHomeStateMenu.tvError.isVisible = true
                },
            )
        }
    }

    private fun loadProfileData() {
        if (homeViewModel.isLoggedIn()) {
            homeViewModel.getCurrentUser()?.let { user ->
                binding.layoutHeader.tvProfileName.text = getString(R.string.text_name, user.username)
            }
        } else {
            binding.layoutHeader.tvProfileName.text = getString(R.string.text_name_user)
        }
    }

    private fun bindMenuList(data: List<Menu>) {
        menuAdapter.submitData(data)
    }

    private fun setButtonImage(isUsingGrid: Boolean) {
        binding.listMenu.ibSortMenu.setImageResource(if (isUsingGrid) R.drawable.ic_menu else R.drawable.ic_sort_menu)
    }

    private fun navigateToDetail(item: Menu) {
        DetailMenuActivity.startActivity(requireContext(), item)
    }

    private fun setClickActionMenu() {
        binding.listMenu.ibSortMenu.setOnClickListener {
            homeViewModel.changeListMode()
        }
    }
}
