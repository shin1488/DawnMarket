package kr.co.teamfresh.syc.dawnmarket.data.network

import android.util.Log
import kr.co.teamfresh.syc.dawnmarket.data.models.AppMainQuickMenuDTO
import java.lang.Exception

class QuickMenuRepository(private val apiService: ApiService) {
    suspend fun getQuickMenus(): List<AppMainQuickMenuDTO> {
        return try {
            val response = apiService.getQuickMenus()
            response.data
        } catch (e: Exception) {
            e.message?.let { Log.e("fetch exception", it) }
            emptyList()
        }
    }
}