package kr.co.teamfresh.syc.dawnmarket.data.network

import kr.co.teamfresh.syc.dawnmarket.data.models.ListResultAppDispClasInfoDTO
import kr.co.teamfresh.syc.dawnmarket.data.models.ListResultAppMainQuickMenuDTO
import kr.co.teamfresh.syc.dawnmarket.data.models.PageResponseAppGoodsInfoDTO
import kr.co.teamfresh.syc.dawnmarket.data.models.SingleResultAppDispClasInfoBySubDispClasInfoDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    //카테고리 조회
    @GET("app/disp-clas-infos/disp-clas-nm")
    suspend fun getCategories(): ListResultAppDispClasInfoDTO

    //퀵메뉴 조회
    @GET("app/main-infos/quick-menu")
    suspend fun getQuickMenus(): ListResultAppMainQuickMenuDTO

    //카테고리 하위목록 조회
    @GET("app/disp-clas-infos/disp-clas/{dispClasSeq}/sub-disp-clas-infos")
    suspend fun getSubCategories(@Path("dispClasSeq") dispClasSeq: Long): SingleResultAppDispClasInfoBySubDispClasInfoDTO

    @GET("app/disp-clas-infos/disp-clas/{dispClasSeq}/sub-disp-clas/{prntsDispClasSeq}/goods-infos")
    suspend fun getProducts(
        @Path("dispClasSeq") dispClasSeq: Int,
        @Path("prntsDispClasSeq") prntsDispClasSeq: Int,
        @Query("page") page: Int = 0,
        @Query("size") size: Int = 20,
        @Query("searchValue") searchValue: String = "추천순"
    ): PageResponseAppGoodsInfoDTO
}
