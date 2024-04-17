package com.fhdufhdu.nocssboard.repository.post

import com.fhdufhdu.nocssboard.repository.post.dto.condition.PostSummariesCondition
import com.fhdufhdu.nocssboard.repository.post.dto.projection.PostSummariesProjection
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PostQueryDsl {
    fun findPostSummaries(condition: PostSummariesCondition, pageable: Pageable): Page<PostSummariesProjection>
}