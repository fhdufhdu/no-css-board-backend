package com.fhdufhdu.windows7board.repository.post

import com.fhdufhdu.windows7board.entity.Post
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository: JpaRepository<Post, Long>, PostQueryDsl {
    fun findByIdAndStatus(id: Long, status: Post.Status): Post?

    fun existsByIdAndStatus(id: Long, status: Post.Status): Boolean
}