package com.fhdufhdu.nocssboard.domain.board.service.dto.result

import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

@Schema(name = "PostSummaries")
class PostSummaries(
    val posts: List<PostSummary>,
    number: Int,
    totalPages: Int,
) {
    class PostSummary (
        val id: Long,
        val userId: String,
        val title: String,
        val content: String,
        val createdAt: Date,
        val updatedAt: Date?
    )
}