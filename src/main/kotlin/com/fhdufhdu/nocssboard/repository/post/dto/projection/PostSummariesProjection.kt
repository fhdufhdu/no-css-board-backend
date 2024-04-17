package com.fhdufhdu.nocssboard.repository.post.dto.projection

import java.sql.Timestamp

class PostSummariesProjection (
    val id: Long,
    val userId: String,
    val title: String,
    val content: String,
    val createdAt: Timestamp,
    val updatedAt: Timestamp?
)