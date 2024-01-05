package kr.co.teamfresh.syc.dawnmarket.data.network

import android.util.Log
import kr.co.teamfresh.syc.dawnmarket.data.models.AppMainQuickMenuDTO
import kr.co.teamfresh.syc.dawnmarket.data.models.AppSubDispClasInfoDTO
import java.lang.Exception

class SubCategoryRepository(private val apiService: ApiService) {
    suspend fun getSubCategories(dispClasSeq: Long): List<AppSubDispClasInfoDTO> {
        return try {
            val response = apiService.getSubCategories(dispClasSeq)
            response.data.appSubDispClasInfoList
        } catch (e: Exception) {
            e.message?.let { Log.e("fetch exception", it) }
            emptyList()
        }
    }
}