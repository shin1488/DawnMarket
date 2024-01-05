package kr.co.teamfresh.syc.dawnmarket.data.network

import android.util.Log
import kr.co.teamfresh.syc.dawnmarket.data.models.PageResponseAppGoodsInfoDTO
import kr.co.teamfresh.syc.dawnmarket.data.models.PaginationDTO
import java.lang.Exception

class ProductRepository(private val apiService: ApiService) {
    suspend fun getProductsInfo(dispClasSeq: Int, prntsDispClasSeq: Int, selectedOrdering: String): PageResponseAppGoodsInfoDTO {
        return try {
            apiService.getProducts(dispClasSeq, prntsDispClasSeq, searchValue = selectedOrdering)
        } catch (e: Exception) {
            e.message?.let { Log.e("fetch exception", it) }
            PageResponseAppGoodsInfoDTO(emptyList(), PaginationDTO(0, 0, 0, 0))
        }
    }
}