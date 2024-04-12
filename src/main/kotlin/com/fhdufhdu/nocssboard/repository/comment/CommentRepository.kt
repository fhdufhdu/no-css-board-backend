package com.fhdufhdu.nocssboard.repository.comment

import com.fhdufhdu.nocssboard.entity.Comment
import com.fhdufhdu.nocssboard.entity.Post
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface CommentRepository: JpaRepository<Comment, Long> {
    fun findAllByPostIdAndStatus(postId: Long, status: Comment.Status, pageable: Pageable): Page<Comment>

    fun findByIdAndPostIdAndStatus(id: Long, postId: Long, status: Comment.Status): Comment?
}