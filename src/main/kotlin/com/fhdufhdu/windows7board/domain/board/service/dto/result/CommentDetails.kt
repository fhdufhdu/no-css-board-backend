package com.fhdufhdu.windows7board.domain.board.service.dto.result

import com.fhdufhdu.windows7board.common.dto.CommonPaginationDto
import java.util.*

class CommentDetails (
    val comments: List<CommentDetail>,
    number: Int,
    totalPages: Int,
    totalElements: Long,
): CommonPaginationDto.Response(number, totalPages, totalElements){
    class CommentDetail(
        val id: Long,
        val userId: String,
        val content:String,
        val createdAt: Date,
        val updatedAt: Date?
    )
}