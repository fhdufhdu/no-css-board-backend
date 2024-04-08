package com.fhdufhdu.nocssboard.repository

import com.fhdufhdu.nocssboard.entity.Comment
import com.fhdufhdu.nocssboard.entity.Post
import org.springframework.data.jpa.repository.JpaRepository

interface CommentRepository: JpaRepository<Comment, Long> {
}