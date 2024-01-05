package kr.co.teamfresh.syc.dawnmarket.data.models

data class PageResponseAppGoodsInfoDTO (
    val data: List<AppGoodsInfoDTO>,
    val pagination: PaginationDTO
)