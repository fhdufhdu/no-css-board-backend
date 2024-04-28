package com.fhdufhdu.windows7board.repository.post

import com.fhdufhdu.windows7board.entity.Post
import com.fhdufhdu.windows7board.entity.QPost
import com.fhdufhdu.windows7board.repository.post.dto.condition.PostSummariesCondition
import com.fhdufhdu.windows7board.repository.post.dto.projection.PostSummariesProjection
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable


class PostQueryDslImpl(
    private val queryFactory: JPAQueryFactory
) : PostQueryDsl {
    override fun findPostSummaries(condition: PostSummariesCondition, pageable: Pageable): Page<PostSummariesProjection> {
        val post = QPost.post

        val whereConditions = arrayOf(QPost.post.status.eq(Post.Status.PUBLISHED), condition.search.searchExpression)

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
            .where(*whereConditions)
            .orderBy(condition.order.orderSpecifier)
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
        val countQuery = queryFactory
            .select(post.count())
            .from(post)
            .where(*whereConditions)

        // 쿼리 실행
        val posts = query.fetch()
        val count = countQuery.fetchOne()!!

        return PageImpl(posts, pageable, count)
    }

}