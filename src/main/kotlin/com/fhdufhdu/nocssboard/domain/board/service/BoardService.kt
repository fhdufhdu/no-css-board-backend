package com.fhdufhdu.nocssboard.domain.board.service

import com.fhdufhdu.nocssboard.domain.board.service.dto.FindPostsDto
import com.fhdufhdu.nocssboard.repository.CommentRepository
import com.fhdufhdu.nocssboard.repository.PostRepository
import com.fhdufhdu.nocssboard.repository.querydsl.PostQueryDsl
import com.fhdufhdu.nocssboard.repository.querydsl.impl.PostQueryDslImpl
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service

@Service
class BoardService(
    private val postRepository: PostRepository,
    private val commentRepository: CommentRepository
) {
    fun findPosts(input: FindPostsDto.Input): FindPostsDto.Return {
        val dbDirection = input.sort.direction.toJpaDirection()
        val pageable = PageRequest.of(input.page.number, input.page.size, Sort.by(dbDirection, input.sort.criteria.value))

        val page = postRepository.findAllBySearch(input.search?.searchQuery, input.search?.searchCriteria?.value, pageable)

        val postDtoList = page.content.stream().map {
            FindPostsDto.Return.Post(it.id!!, it.user.id, it.title, it.content, it.createdAt, it.updatedAt)
        }.toList()

        return FindPostsDto.Return(postDtoList, page.number, page.totalPages)
    }
}