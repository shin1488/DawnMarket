package kr.co.teamfresh.syc.dawnmarket.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kr.co.teamfresh.syc.dawnmarket.data.models.AppDispClasInfoDTO
import kr.co.teamfresh.syc.dawnmarket.data.network.ProductDataRepository
import kr.co.teamfresh.syc.dawnmarket.data.network.RetrofitInstance

class CategoryViewModel(private val repository: ProductDataRepository) : ViewModel() {
    private val _categories = MutableStateFlow<List<AppDispClasInfoDTO>>(emptyList())
    val categories: StateFlow<List<AppDispClasInfoDTO>> = _categories.asStateFlow()

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            _categories.value = repository.getMainCategories()
            Log.d("length", _categories.value.size.toString())
        }
    }

    class CategoryViewModelFactory(private val repository: ProductDataRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CategoryViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}