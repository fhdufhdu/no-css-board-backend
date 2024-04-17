package com.fhdufhdu.nocssboard.domain.board.controller.dto

import com.fhdufhdu.nocssboard.common.dto.CommonPaginationDto

class PostCommentDetailsRequest(
    page_number: Int,
    page_size: Int
) : CommonPaginationDto.Request(page_number, page_size)