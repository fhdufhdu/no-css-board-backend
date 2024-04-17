package com.fhdufhdu.nocssboard.domain.board.service.dto.result

import java.util.*

class PostDetail(
    val id: Long,
    val userId: String,
    val title: String,
    val content: String,
    val createdAt: Date,
    val updatedAt: Date?
)