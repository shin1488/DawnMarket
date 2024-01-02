package kr.co.teamfresh.syc.dawnmarket.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kr.co.teamfresh.syc.dawnmarket.data.models.AppMainQuickMenuDTO
import kr.co.teamfresh.syc.dawnmarket.data.network.QuickMenuRepository

class QuickMenuViewModel(private val repository: QuickMenuRepository) : ViewModel() {
    private val _quickMenus = MutableStateFlow<List<AppMainQuickMenuDTO>>(emptyList())
    val quickMenus: StateFlow<List<AppMainQuickMenuDTO>> = _quickMenus.asStateFlow()

    init {
        fetchQuickMenus()
    }

    private fun fetchQuickMenus() {
        viewModelScope.launch {
            _quickMenus.value = repository.getQuickMenus()
        }
    }

    //ViewModel에 추가 인자가 필요하기 때문에, Factory 인터페이스를 구현하여 생성해야 함.
    class QuickMenuViewModelFactory(private val repository: QuickMenuRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(QuickMenuViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return QuickMenuViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}