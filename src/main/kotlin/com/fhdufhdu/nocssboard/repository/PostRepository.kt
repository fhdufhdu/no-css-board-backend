package com.fhdufhdu.nocssboard.repository

import com.fhdufhdu.nocssboard.entity.Post
import com.fhdufhdu.nocssboard.repository.querydsl.PostQueryDsl
import org.springframework.data.jpa.repository.JpaRepository

interface PostRepository: JpaRepository<Post, Long>, PostQueryDsl {
}