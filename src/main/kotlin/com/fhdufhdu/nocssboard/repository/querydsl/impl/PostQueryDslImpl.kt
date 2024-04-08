package com.fhdufhdu.nocssboard.repository.querydsl.impl

import com.fhdufhdu.nocssboard.domain.board.service.dto.FindPostsEnum
import com.fhdufhdu.nocssboard.entity.Post
import com.fhdufhdu.nocssboard.entity.QPost
import com.fhdufhdu.nocssboard.repository.querydsl.PostQueryDsl
import com.querydsl.core.support.QueryBase
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException


class PostQueryDslImpl(
    private val queryFactory: JPAQueryFactory
): PostQueryDsl {
    override fun findAllBySearch(searchQuery: String?, searchCriteria: String?, pageable: Pageable): Page<Post> {
        val searchCriteriaEnum = FindAllBySearch_Criteria.from(searchCriteria)

        fun <T> getSearchQuery(jpaQuery: JPAQuery<T>, searchQuery: String?, searchCriteria: FindAllBySearch_Criteria?, post: QPost): JPAQuery<T>{
            var tempQuery = jpaQuery
            if (searchQuery != null && searchCriteria != null) {
                val likeQuery = "%$searchQuery%"
                tempQuery = when(searchCriteria){
                    FindAllBySearch_Criteria.TITLE -> tempQuery.where(post.title.like(likeQuery))
                    FindAllBySearch_Criteria.CONTENT -> tempQuery.where(post.content.like(likeQuery))
                    FindAllBySearch_Criteria.USER_ID -> tempQuery.where(post.user.id.like(likeQuery))
                }
            }
            return tempQuery
        }

        val post = QPost.post
        var query = queryFactory.selectFrom(post).offset(pageable.offset).limit(pageable.pageSize.toLong())

        query = getSearchQuery(query, searchQuery, searchCriteriaEnum, post)
        val posts = query.fetch()

        var countQuery = queryFactory.select(post.count()).from(post)
        countQuery = getSearchQuery(countQuery, searchQuery, searchCriteriaEnum, post)

        val count = countQuery.fetchOne()!!

        return PageImpl(posts, pageable, count)
    }


    enum class FindAllBySearch_Criteria(val value:String){
        TITLE("title"), CONTENT("content"), USER_ID("user_id");

        companion object {
            fun from(s: String?): FindAllBySearch_Criteria? {
                return FindAllBySearch_Criteria.entries.find{ it.value == s }
            }
        }
    }


}