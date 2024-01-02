package kr.co.teamfresh.syc.dawnmarket.data.network

import android.util.Log
import kr.co.teamfresh.syc.dawnmarket.data.models.AppDispClasInfoDTO
import java.lang.Exception

class CategoryRepository(private val apiService: ApiService) {
    suspend fun getCategories(): List<AppDispClasInfoDTO> {
        return try {
            val response = apiService.getCategories()
            response.data
        } catch (e: Exception) {
            e.message?.let { Log.e("fetch exception", it) }
            emptyList()
        }
    }
}