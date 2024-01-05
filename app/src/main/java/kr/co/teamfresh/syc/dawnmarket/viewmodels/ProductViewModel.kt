package kr.co.teamfresh.syc.dawnmarket.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kr.co.teamfresh.syc.dawnmarket.data.DispClasSeqManager
import kr.co.teamfresh.syc.dawnmarket.data.models.AppGoodsInfoDTO
import kr.co.teamfresh.syc.dawnmarket.data.network.ProductRepository
import kr.co.teamfresh.syc.dawnmarket.data.network.QuickMenuRepository

class ProductViewModel(private val repository: ProductRepository) : ViewModel() {
    private val _products = MutableStateFlow<List<AppGoodsInfoDTO>>(emptyList())
    val products: StateFlow<List<AppGoodsInfoDTO>> = _products.asStateFlow()

    init {
        viewModelScope.launch {
            DispClasSeqManager.combinedFlow.collect { (prntsDispClasSeq, dispClasSeq) ->
                Log.d("dadsdasd", "${prntsDispClasSeq}, ${dispClasSeq}")
                fetchProducts(prntsDispClasSeq, dispClasSeq)
            }
        }
    }

    private fun fetchProducts(prntsDispClasSeq: Long, dispClasSeq: Int) {
        viewModelScope.launch {
            DispClasSeqManager.subCategorySize.collect { subCategorySize ->
                if (dispClasSeq == -1) {
                    val combinedProducts = mutableListOf<AppGoodsInfoDTO>()
                    for (seq in 1 .. subCategorySize) {
                        Log.d("dasda", "${prntsDispClasSeq.toInt()}, ${prntsDispClasSeq.toInt() + seq}")
                        val products = repository.getProducts(prntsDispClasSeq.toInt(), prntsDispClasSeq.toInt() + seq)
                        combinedProducts.addAll(products)
                    }
                    _products.value = combinedProducts
                }
                if (prntsDispClasSeq != -1L && dispClasSeq != -1) {
                    _products.value = repository.getProducts(prntsDispClasSeq.toInt(), dispClasSeq)
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