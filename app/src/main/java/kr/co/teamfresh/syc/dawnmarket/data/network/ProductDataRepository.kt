package kr.co.teamfresh.syc.dawnmarket.data.network

import android.util.Log
import kr.co.teamfresh.syc.dawnmarket.data.models.AppDispClasInfoDTO
import java.lang.Exception

class ProductDataRepository(private val apiService: ApiService) {
    suspend fun getMainCategories(): List<AppDispClasInfoDTO> {
        return try {
            val response = apiService.getMainCategories()
            response.data
        } catch (e: Exception) {
            e.message?.let { Log.d("eexx", it) }
            Log.d("eee", "없는데 ㅂㅅㅋㅋ")
            emptyList()
        }
    }
}