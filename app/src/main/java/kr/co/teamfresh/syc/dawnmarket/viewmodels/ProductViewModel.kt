package kr.co.teamfresh.syc.dawnmarket.viewmodels

import android.util.Log
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kr.co.teamfresh.syc.dawnmarket.data.StateFlowManager
import kr.co.teamfresh.syc.dawnmarket.data.models.AppGoodsInfoDTO
import kr.co.teamfresh.syc.dawnmarket.data.network.ProductRepository
import kr.co.teamfresh.syc.dawnmarket.ui.activity.CategoryDetailActivity

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {
    private val _products = MutableStateFlow<List<AppGoodsInfoDTO>>(emptyList())
    val products: StateFlow<List<AppGoodsInfoDTO>> = _products.asStateFlow()

    init {
        viewModelScope.launch {
            StateFlowManager.apiIngredients.collect { (prntsDispClasSeq, dispClasSeq, selectedOrdering, noDisplayItemCount) ->
                fetchProducts(prntsDispClasSeq, dispClasSeq, selectedOrdering,noDisplayItemCount)
            }
        }
    }

    private fun fetchProducts(prntsDispClasSeq: Long, dispClasSeq: Int, selectedOrdering: String, noDisplayItemCount: Int) {
        viewModelScope.launch {
            StateFlowManager.subCategorySize.collect { subCategorySize ->
                if (dispClasSeq == -1) {
                    val combinedProducts = mutableListOf<AppGoodsInfoDTO>()
                    var productNum = 0
                    for (seq in 1 .. subCategorySize) {
                        val pageResponseAppGoodsInfoDTO = repository.getProductsInfo(prntsDispClasSeq.toInt(), prntsDispClasSeq.toInt() + seq, selectedOrdering)
                        val products = pageResponseAppGoodsInfoDTO.data
                        productNum += pageResponseAppGoodsInfoDTO.pagination.totalElements
                        combinedProducts.addAll(products)
                    }
                    StateFlowManager.setProductNum(productNum - noDisplayItemCount)
                    _products.value = combinedProducts
                }
                if (prntsDispClasSeq != -1L && dispClasSeq != -1) {
                    val pageResponseAppGoodsInfoDTO = repository.getProductsInfo(prntsDispClasSeq.toInt(), dispClasSeq, selectedOrdering)
                    _products.value = pageResponseAppGoodsInfoDTO.data
                    StateFlowManager.noDisplayItemCount.collect { noDisPlayItemCount ->
                        StateFlowManager.setProductNum(pageResponseAppGoodsInfoDTO.pagination.totalElements - noDisPlayItemCount)
                    }
                }
            }
        }
    }

    class ProductViewModelFactory(private val repository: ProductRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ProductViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ProductViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}