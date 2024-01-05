package kr.co.teamfresh.syc.dawnmarket.data.models

data class PaginationDTO (
    val currentPage: Int,
    val elementSizeOfPage: Int,
    val totalElements: Int,
    val totalPage: Int
)