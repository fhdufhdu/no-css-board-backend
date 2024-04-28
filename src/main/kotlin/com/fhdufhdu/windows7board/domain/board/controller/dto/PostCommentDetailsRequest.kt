package com.fhdufhdu.windows7board.domain.board.controller.dto

import com.fhdufhdu.windows7board.common.dto.CommonPaginationDto

class PostCommentDetailsRequest(
    page_number: Int,
    page_size: Int
) : CommonPaginationDto.Request(page_number, page_size)