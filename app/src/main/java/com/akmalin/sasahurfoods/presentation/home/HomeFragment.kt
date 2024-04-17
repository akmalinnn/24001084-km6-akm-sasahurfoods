package com.akmalin.sasahurfoods.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.akmalin.sasahurfoods.R
import com.akmalin.sasahurfoods.data.datasource.category.CategoryApiDataSource
import com.akmalin.sasahurfoods.data.datasource.menu.MenuApiDataSource
import com.akmalin.sasahurfoods.data.model.Category
import com.akmalin.sasahurfoods.data.model.Menu
import com.akmalin.sasahurfoods.data.repository.CategoryRepositoryImpl
import com.akmalin.sasahurfoods.data.repository.MenuRepositoryImpl
import com.akmalin.sasahurfoods.data.source.local.pref.UserPreferenceImpl
import com.akmalin.sasahurfoods.data.source.network.services.FoodAppApiService
import com.akmalin.sasahurfoods.databinding.FragmentHomeBinding
import com.akmalin.sasahurfoods.presentation.detailfood.DetailMenuActivity
import com.akmalin.sasahurfoods.presentation.home.adapter.CategoryAdapter
import com.akmalin.sasahurfoods.presentation.home.adapter.MenuAdapter
import com.akmalin.sasahurfoods.utils.GenericViewModelFactory
import com.akmalin.sasahurfoods.utils.proceedWhen

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    private val menuAdapter: MenuAdapter by lazy {
        MenuAdapter(viewModel.getListMode()) {
            navigateToDetail(it)
        }
    }

    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter {
            getMenuList(it.name)
        }
    }

    private val viewModel: HomeViewModel by viewModels {
        val service = FoodAppApiService.invoke()
        val menuDataSource = MenuApiDataSource(service)
        val menuRepository = MenuRepositoryImpl(menuDataSource)
        val categoryDataSource = CategoryApiDataSource(service)
        val userPreference = UserPreferenceImpl(requireContext())
        val categoryRepository = CategoryRepositoryImpl(categoryDataSource)
        GenericViewModelFactory.create(HomeViewModel(categoryRepository, menuRepository, userPreference))
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
            layoutManager =
               LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        }
    }

    private fun observeGridMode() {
        viewModel.isUsingGridMode.observe(viewLifecycleOwner) { isUsingGridMode ->
            setButtonImage(isUsingGridMode)
            changeLayoutMode(isUsingGridMode)
        }
    }

    private fun bindCategory(data: List<Category>) {
        categoryAdapter.submitData(data)
    }



    private fun getCategoryList() {
        viewModel.getCategories().observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data -> bindCategory(data) }
                }
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
        viewModel.getMenus(name).observe(viewLifecycleOwner) {
            it.proceedWhen(
                doOnSuccess = {
                    it.payload?.let { data ->
                        bindMenuList(data)
                    }
                }
            )
        }
    }

    private fun bindMenuList(data : List<Menu>) {
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
            viewModel.changeListMode()
        }
    }
}
