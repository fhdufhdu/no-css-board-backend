package com.fhdufhdu.nocssboard.repository.post

import com.fhdufhdu.nocssboard.entity.Post
import com.fhdufhdu.nocssboard.repository.post.dto.PostRepositoryDto
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface PostQueryDsl {
    fun findAllBySearch(search: PostRepositoryDto.FindAllBySearch.SearchCondition?, order: PostRepositoryDto.FindAllBySearch.OrderCondition, pageable: Pageable): Page<Post>
}