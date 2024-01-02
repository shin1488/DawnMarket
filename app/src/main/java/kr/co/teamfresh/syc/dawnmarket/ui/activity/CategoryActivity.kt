package kr.co.teamfresh.syc.dawnmarket.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import kr.co.teamfresh.syc.dawnmarket.R
import kr.co.teamfresh.syc.dawnmarket.data.network.ProductDataRepository
import kr.co.teamfresh.syc.dawnmarket.data.network.RetrofitInstance
import kr.co.teamfresh.syc.dawnmarket.databinding.ActivityCategoryBinding
import kr.co.teamfresh.syc.dawnmarket.ui.adapters.CategoryAdapter
import kr.co.teamfresh.syc.dawnmarket.viewmodels.CategoryViewModel

class CategoryActivity : AppCompatActivity() {
    private lateinit var viewModel: CategoryViewModel
    private lateinit var binding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category)

        val api = RetrofitInstance.api
        val repository = ProductDataRepository(api)
        val viewModelFactory = CategoryViewModel.CategoryViewModelFactory(repository)
        viewModel = ViewModelProvider(this, viewModelFactory)[CategoryViewModel::class.java]


        val adapter = CategoryAdapter(emptyList())
        binding.categoryRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.categoryRecyclerView.adapter = adapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.categories.collect { categories ->
                    adapter.updateCategories(categories)
                }
            }
        }
    }
}
