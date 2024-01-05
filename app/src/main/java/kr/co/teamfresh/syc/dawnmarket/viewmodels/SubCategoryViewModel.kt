package kr.co.teamfresh.syc.dawnmarket.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.launch
import kr.co.teamfresh.syc.dawnmarket.data.DispClasSeqManager
import kr.co.teamfresh.syc.dawnmarket.data.models.AppSubDispClasInfoDTO
import kr.co.teamfresh.syc.dawnmarket.data.network.SubCategoryRepository

class SubCategoryViewModel(private val repository: SubCategoryRepository, private val prntsDispClasSeq: Long) : ViewModel() {
    private val _subCategories = MutableStateFlow(listOf(AppSubDispClasInfoDTO("", "", -1, -1, "상품 전체")))
    val subCategories: StateFlow<List<AppSubDispClasInfoDTO>> = _subCategories.asStateFlow()

    init {
        fetchSubCategories()
    }

    private fun fetchSubCategories() {
        viewModelScope.launch {
            _subCategories.value = repository.getSubCategories(prntsDispClasSeq)
            DispClasSeqManager.setCategorySize(subCategories.value.size)
        }
    }

    fun onSubCategoryNameClicked(dispClasSeq: Int) {
        DispClasSeqManager.setDispClasSeq(dispClasSeq)
    }

    class SubCategoryViewModelFactory(private val repository: SubCategoryRepository, private val prntsDispClasSeq: Long) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SubCategoryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SubCategoryViewModel(repository, prntsDispClasSeq) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}