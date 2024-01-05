package kr.co.teamfresh.syc.dawnmarket.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

object DispClasSeqManager {
    private val _prntsDispClasSeq = MutableStateFlow<Long>(-1)
    val prntsDispClasSeq: StateFlow<Long> = _prntsDispClasSeq.asStateFlow()

    private val _dispClasSeq = MutableStateFlow<Int>(-1)
    val dispClasSeq: StateFlow<Int> = _dispClasSeq.asStateFlow()

    private val _subCategorySize = MutableStateFlow(0)
    val subCategorySize: StateFlow<Int> = _subCategorySize.asStateFlow()

    private val _combinedFlow = MutableStateFlow<Pair<Long, Int>>(-1L to -1)
    val combinedFlow: StateFlow<Pair<Long, Int>> = _combinedFlow.asStateFlow()

    init {
        combine(prntsDispClasSeq, dispClasSeq) { prntsDispClasSeq, dispClasSeq ->
            Pair(prntsDispClasSeq, dispClasSeq)
        }
            .onEach { combinedValue ->
                _combinedFlow.value = combinedValue
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
}
