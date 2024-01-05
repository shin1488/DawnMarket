package kr.co.teamfresh.syc.dawnmarket.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.coroutines.launch
import kr.co.teamfresh.syc.dawnmarket.R
import kr.co.teamfresh.syc.dawnmarket.adapters.ProductAdapter
import kr.co.teamfresh.syc.dawnmarket.adapters.SubCategoryAdapter
import kr.co.teamfresh.syc.dawnmarket.data.StateFlowManager
import kr.co.teamfresh.syc.dawnmarket.data.models.PageResponseAppGoodsInfoDTO
import kr.co.teamfresh.syc.dawnmarket.data.network.ProductRepository
import kr.co.teamfresh.syc.dawnmarket.data.network.RetrofitInstance
import kr.co.teamfresh.syc.dawnmarket.data.network.SubCategoryRepository
import kr.co.teamfresh.syc.dawnmarket.databinding.ActivityCategoryDetailBinding
import kr.co.teamfresh.syc.dawnmarket.utils.ToastUtils
import kr.co.teamfresh.syc.dawnmarket.viewmodels.ProductViewModel
import kr.co.teamfresh.syc.dawnmarket.viewmodels.SubCategoryViewModel

class CategoryDetailActivity : AppCompatActivity(), OnClickListener {
    private lateinit var binding: ActivityCategoryDetailBinding
    private lateinit var subCategoryViewModel: SubCategoryViewModel
    private lateinit var productViewModel: ProductViewModel

    private lateinit var orderingTextViews: List<TextView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category_detail)

        val dispClasSeq = intent.getLongExtra("dispClasSeq", 0)
        val dispClasNm = intent.getStringExtra("dispClasNm")
        binding.detailTitleText.text = dispClasNm

        val api = RetrofitInstance.api

        val subCategoryRepository = SubCategoryRepository(api)
        val subCategoryViewModelFactory =
            SubCategoryViewModel.SubCategoryViewModelFactory(subCategoryRepository, dispClasSeq)
        subCategoryViewModel =
            ViewModelProvider(this, subCategoryViewModelFactory)[SubCategoryViewModel::class.java]

        val subCategoryAdapter = SubCategoryAdapter(emptyList(), subCategoryViewModel)
        binding.detailSubCategoryRecyclerView.layoutManager = GridLayoutManager(this, 3)
        binding.detailSubCategoryRecyclerView.adapter = subCategoryAdapter

        val productRepository = ProductRepository(api)
        val productViewModelFactory = ProductViewModel.ProductViewModelFactory(productRepository)
        productViewModel =
            ViewModelProvider(this, productViewModelFactory)[ProductViewModel::class.java]

        val productAdapter = ProductAdapter(emptyList())
        binding.detailProductRecyclerView.layoutManager = GridLayoutManager(this, 2)
        binding.detailProductRecyclerView.adapter = productAdapter

        binding.detailRecommendOrderingText.setTextAppearance(R.style.Selected)

        orderingTextViews = listOf(
            binding.detailRecommendOrderingText,
            binding.detailSellingOrderingText,
            binding.detailLowPriceOrderingText,
            binding.detailHighPriceOrderingText
        )

        orderingTextViews.forEach { textView ->
            textView.setOnClickListener { view ->
                onOrderingTextViewClicked(view as TextView)
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    subCategoryViewModel.subCategories.collect { subCategories ->
                        subCategoryAdapter.updateSubCategories(subCategories)
                    }
                }
                launch {
                    productViewModel.products.collect { products ->
                        productAdapter.updateProducts(products)
                    }
                }
                launch {
                    StateFlowManager.productNum.collect { productNum ->
                        binding.detailProductNumText.text = "${productNum}건의 검색결과"
                    }
                }
            }
        }

        binding.detailBackButton.setOnClickListener(this)
        binding.detailSearchButton.setOnClickListener(this)
        binding.detailBasketButton.setOnClickListener(this)
    }

    override fun onDestroy() { //값 초기화
        StateFlowManager.setPrntsDispClasSeq(-1)
        StateFlowManager.setDispClasSeq(-1)
        StateFlowManager.setCategorySize(0)
        super.onDestroy()
    }

    override fun onClick(v: View?) {
        if (v == null) return
        when (v.id) {
            R.id.detail_back_button -> finish()

            R.id.detail_search_button,
            R.id.detail_basket_button -> {
                ToastUtils.showDevelopmentToast(this)
            }
        }
    }

    private fun onOrderingTextViewClicked(clickedTextView: TextView) {
        orderingTextViews.forEach { textView ->
            if (textView == clickedTextView) {
                textView.setTextAppearance(R.style.Selected)
            } else {
                textView.setTextAppearance(R.style.Unselected)
            }
        }
        StateFlowManager.setSelectedOrdering(clickedTextView.text.toString())
    }
}