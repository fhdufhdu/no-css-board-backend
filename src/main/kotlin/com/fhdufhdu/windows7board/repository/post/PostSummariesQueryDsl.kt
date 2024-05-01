package com.fhdufhdu.windows7board.repository.post

import com.fhdufhdu.windows7board.repository.post.dto.condition.PostSummariesCondition
import com.fhdufhdu.windows7board.repository.post.dto.projection.PostSummariesProjection
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PostSummariesQueryDsl {
    fun findPostSummaries(condition: PostSummariesCondition, pageable: Pageable): Page<PostSummariesProjection>
}