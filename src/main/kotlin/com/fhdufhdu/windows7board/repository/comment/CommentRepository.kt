package com.fhdufhdu.windows7board.repository.comment

import com.fhdufhdu.windows7board.entity.Comment
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long> {
    fun findAllByPostIdAndStatus(postId: Long, status: Comment.Status, pageable: Pageable): Page<Comment>

    fun findByIdAndPostIdAndStatus(id: Long, postId: Long, status: Comment.Status): Comment?
}