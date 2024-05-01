package com.fhdufhdu.windows7board.repository.post

import com.fhdufhdu.windows7board.entity.Post
import com.fhdufhdu.windows7board.entity.QPost
import com.fhdufhdu.windows7board.repository.post.dto.condition.PostSummariesCondition
import com.fhdufhdu.windows7board.repository.post.dto.projection.PostSummariesProjection
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable


open class PostSummariesQueryDslImpl(
    private val queryFactory: JPAQueryFactory
) : PostSummariesQueryDsl {
    override fun findPostSummaries(
        condition: PostSummariesCondition,
        pageable: Pageable
    ): Page<PostSummariesProjection> {
        val post = QPost.post

        // 쿼리
        val query = queryFactory
            .select(
                Projections.constructor(
                    PostSummariesProjection::class.java,
                    post.id,
                    post.user.id,
                    post.title,
                    post.content.substring(0, 30),
                    post.createdAt,
                    post.updatedAt
                )
            )
            .from(post)
            .where(
                searchTitle(condition.search),
                searchContent(condition.search),
                searchUserId(condition.search),
                isPublished()
            )
            .orderBy(orderBy(condition.order))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
        val countQuery = queryFactory
            .select(post.count())
            .from(post)
            .where(
                searchTitle(condition.search),
                searchContent(condition.search),
                searchUserId(condition.search),
                isPublished()
            )

        // 쿼리 실행
        val posts = query.fetch()
        val count = countQuery.fetchOne()!!

        return PageImpl(posts, pageable, count)
    }

    private fun searchTitle(searchCondition: PostSummariesCondition.Search?): BooleanExpression {
        if (searchCondition != null && searchCondition.searchCriteria in arrayOf(
                PostSummariesCondition.SearchCriteria.TITLE,
                PostSummariesCondition.SearchCriteria.ALL
            )
        ) {
            return QPost.post.title.like("%${searchCondition.searchQuery}%")
        }
        return Expressions.TRUE
    }

    private fun searchContent(searchCondition: PostSummariesCondition.Search?): BooleanExpression {
        if (searchCondition != null && searchCondition.searchCriteria in arrayOf(
                PostSummariesCondition.SearchCriteria.CONTENT,
                PostSummariesCondition.SearchCriteria.ALL
            )
        ) {
            return QPost.post.content.like("%${searchCondition.searchQuery}%")
        }
        return Expressions.TRUE
    }

    private fun searchUserId(searchCondition: PostSummariesCondition.Search?): BooleanExpression {
        if (searchCondition != null && searchCondition.searchCriteria in arrayOf(
                PostSummariesCondition.SearchCriteria.USER_ID,
                PostSummariesCondition.SearchCriteria.ALL
            )
        ) {
            return QPost.post.user.id.like("%${searchCondition.searchQuery}%")
        }
        return Expressions.TRUE
    }

    private fun isPublished(): BooleanExpression {
        return QPost.post.status.eq(Post.Status.PUBLISHED)
    }

    private fun orderBy(orderCondition: PostSummariesCondition.Order): OrderSpecifier<*> {
        val order = orderCondition.order
        return when (orderCondition.orderCriteria) {
            PostSummariesCondition.OrderCriteria.TITLE -> OrderSpecifier(order, QPost.post.title)
            PostSummariesCondition.OrderCriteria.CONTENT -> OrderSpecifier(order, QPost.post.content)
            PostSummariesCondition.OrderCriteria.CREATED_AT -> OrderSpecifier(order, QPost.post.createdAt)
            PostSummariesCondition.OrderCriteria.USER_ID -> OrderSpecifier(order, QPost.post.user.id)
        }
    }

}