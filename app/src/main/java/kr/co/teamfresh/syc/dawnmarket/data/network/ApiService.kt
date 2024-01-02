package kr.co.teamfresh.syc.dawnmarket.data.network

import kr.co.teamfresh.syc.dawnmarket.data.models.ListResultAppDispClasInfoDTO
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    //카테고리 조회
    @GET("app/disp-clas-infos/disp-clas-nm")
    suspend fun getMainCategories(): ListResultAppDispClasInfoDTO
}
