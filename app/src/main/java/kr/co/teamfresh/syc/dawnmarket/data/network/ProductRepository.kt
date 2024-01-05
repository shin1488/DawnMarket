package kr.co.teamfresh.syc.dawnmarket.data.network

import android.util.Log
import kr.co.teamfresh.syc.dawnmarket.data.models.AppGoodsInfoDTO
import kr.co.teamfresh.syc.dawnmarket.data.models.AppMainQuickMenuDTO
import kr.co.teamfresh.syc.dawnmarket.data.models.AppSubDispClasInfoDTO
import java.lang.Exception

class ProductRepository(private val apiService: ApiService) {
    suspend fun getProducts(dispClasSeq: Int, prntsDispClasSeq: Int): List<AppGoodsInfoDTO> {
        return try {
            val response = apiService.getProducts(dispClasSeq, prntsDispClasSeq)
            response.data
        } catch (e: Exception) {
            e.message?.let { Log.e("fetch exception", it) }
            emptyList()
        }
    }
}