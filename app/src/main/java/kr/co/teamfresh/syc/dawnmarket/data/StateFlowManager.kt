package kr.co.teamfresh.syc.dawnmarket.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kr.co.teamfresh.syc.dawnmarket.data.models.FetchIngredients

object StateFlowManager {
    private val _prntsDispClasSeq = MutableStateFlow<Long>(-1)
    val prntsDispClasSeq: StateFlow<Long> = _prntsDispClasSeq.asStateFlow()

    private val _dispClasSeq = MutableStateFlow<Int>(-1)
    val dispClasSeq: StateFlow<Int> = _dispClasSeq.asStateFlow()

    private val _subCategorySize = MutableStateFlow(0)
    val subCategorySize: StateFlow<Int> = _subCategorySize.asStateFlow()

    private val _apiIngredients = MutableStateFlow<FetchIngredients>(FetchIngredients(-1, -1, "", 0))
    val apiIngredients: StateFlow<FetchIngredients> = _apiIngredients.asStateFlow()

    private val _productNum = MutableStateFlow<Int>(0)
    val productNum = _productNum.asStateFlow()

    private val _selectedOrdering = MutableStateFlow<String>("추천순")
    val selectedOrdering = _selectedOrdering.asStateFlow()

    private val _noDisplayItemCount = MutableStateFlow<Int>(0)
    val noDisplayItemCount = _noDisplayItemCount.asStateFlow()

    init {
        combine(prntsDispClasSeq, dispClasSeq, selectedOrdering, noDisplayItemCount) { prntsDispClasSeq, dispClasSeq, selectedOrdering, noDisplayItemCount ->
            FetchIngredients(prntsDispClasSeq, dispClasSeq, selectedOrdering, noDisplayItemCount)
        }
            .onEach { combinedValue ->
                _apiIngredients.value = combinedValue
            }
            .launchIn(CoroutineScope(Dispatchers.Default))
    }

    fun setPrntsDispClasSeq(newPrntsDispClasSeq: Long) {
        _prntsDispClasSeq.value = newPrntsDispClasSeq
    }

    fun setDispClasSeq(newDispClasSeq: Int) {
        _dispClasSeq.value = newDispClasSeq
    }

    fun setCategorySize(newCategorySize: Int) {
        _subCategorySize.value = newCategorySize
    }

    fun setProductNum(newProductNum: Int) {
        _productNum.value = newProductNum
    }

    fun setSelectedOrdering(newSelectedOrdering: String) {
        _selectedOrdering.value = newSelectedOrdering
    }

    fun setNoDisplayItemCount(newNoDisplayItemCount: Int) {
        _noDisplayItemCount.value = newNoDisplayItemCount
    }

    fun clearNoDisplayItemCount() {
        _noDisplayItemCount.value = 0
    }
}
