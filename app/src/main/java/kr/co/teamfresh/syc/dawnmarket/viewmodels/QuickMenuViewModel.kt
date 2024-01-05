package kr.co.teamfresh.syc.dawnmarket.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kr.co.teamfresh.syc.dawnmarket.data.models.AppMainQuickMenuDTO
import kr.co.teamfresh.syc.dawnmarket.data.network.QuickMenuRepository
import javax.inject.Inject

class QuickMenuViewModel(private val repository: QuickMenuRepository) : ViewModel() {
    private val _quickMenus = MutableStateFlow<List<AppMainQuickMenuDTO>>(emptyList())
    val quickMenus: StateFlow<List<AppMainQuickMenuDTO>> = _quickMenus.asStateFlow()

    private val _showToast = MutableStateFlow(false)
    val showToast: StateFlow<Boolean> = _showToast.asStateFlow()

    init {
        fetchQuickMenus()
    }

    private fun fetchQuickMenus() {
        viewModelScope.launch {
            _quickMenus.value = repository.getQuickMenus()
        }
    }

    fun onQuickMenuItemClicked() {
        _showToast.value = true
        _showToast.value = false
    }

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