package com.fhdufhdu.nocssboard.repository.querydsl

import com.fhdufhdu.nocssboard.domain.board.service.dto.FindPostsEnum
import com.fhdufhdu.nocssboard.entity.Post
import com.fhdufhdu.nocssboard.repository.querydsl.impl.PostQueryDslImpl
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PostQueryDsl {
    fun findAllBySearch(searchQuery: String?, searchCriteria: String?, pageable: Pageable): Page<Post>
}