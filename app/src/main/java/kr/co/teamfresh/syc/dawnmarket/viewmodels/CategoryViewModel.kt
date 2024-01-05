package kr.co.teamfresh.syc.dawnmarket.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kr.co.teamfresh.syc.dawnmarket.data.StateFlowManager
import kr.co.teamfresh.syc.dawnmarket.data.models.AppDispClasInfoDTO
import kr.co.teamfresh.syc.dawnmarket.data.network.CategoryRepository

class CategoryViewModel(private val repository: CategoryRepository) : ViewModel() {
    private val _categories = MutableStateFlow<List<AppDispClasInfoDTO>>(emptyList())
    val categories: StateFlow<List<AppDispClasInfoDTO>> = _categories.asStateFlow()

    private val _selectedItem = MutableStateFlow<AppDispClasInfoDTO?>(null)
    val selectedItem: StateFlow<AppDispClasInfoDTO?> = _selectedItem.asStateFlow()

    init {
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            _categories.value = repository.getCategories()
        }
    }

    fun onCategoryItemClicked (item: AppDispClasInfoDTO) {
        _selectedItem.value = item
        StateFlowManager.setPrntsDispClasSeq(item.dispClasSeq)
    }

    fun clearSelectedItem() { _selectedItem.value = null }

    //ViewModel에 추가 인자가 필요하기 때문에, Factory 인터페이스를 구현하여 생성해야 함.
    class CategoryViewModelFactory(private val repository: CategoryRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CategoryViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CategoryViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}