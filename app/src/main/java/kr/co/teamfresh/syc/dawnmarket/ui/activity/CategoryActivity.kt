package kr.co.teamfresh.syc.dawnmarket.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import kr.co.teamfresh.syc.dawnmarket.R
import kr.co.teamfresh.syc.dawnmarket.data.network.CategoryRepository
import kr.co.teamfresh.syc.dawnmarket.data.network.QuickMenuRepository
import kr.co.teamfresh.syc.dawnmarket.data.network.RetrofitInstance
import kr.co.teamfresh.syc.dawnmarket.databinding.ActivityCategoryBinding
import kr.co.teamfresh.syc.dawnmarket.adapters.CategoryAdapter
import kr.co.teamfresh.syc.dawnmarket.adapters.QuickMenuAdapter
import kr.co.teamfresh.syc.dawnmarket.utils.DevelopmentToast
import kr.co.teamfresh.syc.dawnmarket.viewmodels.CategoryViewModel
import kr.co.teamfresh.syc.dawnmarket.viewmodels.QuickMenuViewModel

class CategoryActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var categoryViewModel: CategoryViewModel
    private lateinit var quickMenuViewModel: QuickMenuViewModel
    private lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category)

        val api = RetrofitInstance.api

        val categoryRepository = CategoryRepository(api)
        val categoryViewModelFactory = CategoryViewModel.CategoryViewModelFactory(categoryRepository)
        categoryViewModel = ViewModelProvider(this, categoryViewModelFactory)[CategoryViewModel::class.java]

        val categoryAdapter = CategoryAdapter(emptyList())
        binding.categoryRecyclerViewCategory.layoutManager = GridLayoutManager(this, 4)
        binding.categoryRecyclerViewCategory.adapter = categoryAdapter

        val quickMenuRepository = QuickMenuRepository(api)
        val quickMenuViewModelFactory = QuickMenuViewModel.QuickMenuViewModelFactory(quickMenuRepository)
        quickMenuViewModel = ViewModelProvider(this, quickMenuViewModelFactory)[QuickMenuViewModel::class.java]

        val quickMenuAdapter = QuickMenuAdapter(emptyList())
        binding.categoryRecyclerViewQuickMenu.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding.categoryRecyclerViewQuickMenu.adapter = quickMenuAdapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    categoryViewModel.categories.collect { categories ->
                        categoryAdapter.updateCategories(categories)
                    }
                }
                launch {
                    quickMenuViewModel.quickMenus.collect { quickMenus ->
                        quickMenuAdapter.updateQuickMenus(quickMenus)
                    }
                }
            }
        }
        binding.categoryTextSignIn.setOnClickListener(this)
        binding.categoryImageNotification.setOnClickListener(this)
        binding.categoryImageSetting.setOnClickListener(this)
        binding.categoryBottomNavigationBar.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.category_bottomNavigation_category -> { true }
                else -> {
                    DevelopmentToast.showToast(this)
                    false
                }
            }
        }
    }

    override fun onClick(v: View?) {
        if(v == null) return
        when(v.id) {
            R.id.category_text_signIn,
            R.id.category_image_notification,
            R.id.category_image_setting -> {
                DevelopmentToast.showToast(this)
            }
        }
    }
}
