package com.akmalin.sasahurfoods.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.akmalin.sasahurfoods.R
import com.akmalin.sasahurfoods.data.datasource.category.DummyCategoryDataSource
import com.akmalin.sasahurfoods.data.datasource.menu.DummyMenuDataSource
import com.akmalin.sasahurfoods.data.model.Category
import com.akmalin.sasahurfoods.data.model.Menu
import com.akmalin.sasahurfoods.data.repository.CategoryRepositoryImpl
import com.akmalin.sasahurfoods.data.repository.MenuRepositoryImpl
import com.akmalin.sasahurfoods.databinding.FragmentHomeBinding
import com.akmalin.sasahurfoods.presentation.detailfood.DetailFoodActivity
import com.akmalin.sasahurfoods.presentation.home.adapter.CategoryAdapter
import com.akmalin.sasahurfoods.presentation.home.adapter.MenuAdapter
import com.akmalin.sasahurfoods.presentation.home.adapter.OnItemClickedListener
import com.akmalin.sasahurfoods.utils.GenericViewModelFactory

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private var isUsingGridMode: Boolean = true
    private var adapterMenu: MenuAdapter? = null

    private val viewModel: HomeViewModel by viewModels {
        val menuDataSource = DummyMenuDataSource()
        val menuRepository = MenuRepositoryImpl(menuDataSource)
        val categoryDataSource = DummyCategoryDataSource()
        val categoryRepository = CategoryRepositoryImpl(categoryDataSource)
        GenericViewModelFactory.create(HomeViewModel(categoryRepository, menuRepository))
    }

    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter {
        }
    }

//    private val productAdapter: MenuAdapter by lazy {
//        MenuAdapter {
//
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindCategoryList(viewModel.getCategories())
        bindMenuList(isUsingGridMode)
        setClickActionMenu()
    }

    private fun setClickActionMenu() {
        binding.listMenu.ibSortMenu.setOnClickListener {
            isUsingGridMode = !isUsingGridMode
            setButtonImage(isUsingGridMode)
            bindMenuList(isUsingGridMode)
        }
    }

    private fun bindCategoryList(data: List<Category>) {
        binding.rvCategory.apply {
            adapter = categoryAdapter
        }
        categoryAdapter.submitData(data)
    }


    private fun bindMenuList(isUsingGrid: Boolean) {
        val listMode = if (isUsingGrid) MenuAdapter.MODE_GRID else MenuAdapter.MODE_LIST
        adapterMenu = MenuAdapter(
            listMode = listMode,
            listener = object : OnItemClickedListener<Menu> {
                override fun onItemClicked(item: Menu) {
                    navigateToDetail(item)
                }
            }
        )
        binding.rvMenu.apply {
            adapter = adapterMenu
            layoutManager = GridLayoutManager(requireContext(), if (isUsingGridMode) 2 else 1)
        }
        adapterMenu?.submitData(viewModel.getMenus())
    }

    private fun setButtonImage(isUsingGrid: Boolean) {
        binding.listMenu.ibSortMenu.setImageResource(if (isUsingGrid) R.drawable.ic_menu else R.drawable.ic_sort_menu)
    }

    private fun navigateToDetail(item : Menu) {
        DetailFoodActivity.startActivity(requireContext(),item)
    }
}
