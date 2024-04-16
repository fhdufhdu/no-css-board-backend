package com.fhdufhdu.nocssboard.repository.post

import com.fhdufhdu.nocssboard.entity.Post
import com.fhdufhdu.nocssboard.entity.QPost
import com.fhdufhdu.nocssboard.repository.post.dto.PostRepositoryDto
import com.querydsl.core.types.ExpressionUtils
import com.querydsl.core.types.Projections
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.JPAExpressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable


class PostQueryDslImpl(
    private val queryFactory: JPAQueryFactory
) : PostQueryDsl {
    override fun findAllBySearch(
        search: PostRepositoryDto.FindAllBySearch.SearchCondition?,
        order: PostRepositoryDto.FindAllBySearch.OrderCondition,
        pageable: Pageable
    ): Page<PostRepositoryDto.FindAllBySearch.Posts> {
        val post = QPost.post

        fun isPublished(): BooleanExpression {
            return QPost.post.status.eq(Post.Status.PUBLISHED)
        }

        fun searchLike(condition: PostRepositoryDto.FindAllBySearch.SearchCondition?): BooleanExpression {
            // 검색이 없다면 항상 true
            if (condition == null) return Expressions.asBoolean(true).isTrue()

            val enum = PostRepositoryDto.FindAllBySearch.SearchCriteria.from(condition.searchCriteria)
            return enum.getLikeExpression(condition.searchQuery)
        }

        // 쿼리
        val query = queryFactory
            .select(
                Projections.constructor(
                    PostRepositoryDto.FindAllBySearch.Posts::class.java,
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
                isPublished(),
                searchLike(search)
            )
            .orderBy(order.orderCriteria.getOrderSpecifier(order.order))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
        val countQuery = queryFactory
            .select(post.count())
            .from(post)
            .where(
                isPublished(),
                searchLike(search)
            )

        // 쿼리 실행
        val posts = query.fetch()
        val count = countQuery.fetchOne()!!

        return PageImpl(posts, pageable, count)
    }

}