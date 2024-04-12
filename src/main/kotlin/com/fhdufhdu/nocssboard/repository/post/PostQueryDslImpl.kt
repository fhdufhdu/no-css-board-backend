package com.fhdufhdu.nocssboard.repository.post

import com.fhdufhdu.nocssboard.entity.Post
import com.fhdufhdu.nocssboard.entity.QPost
import com.fhdufhdu.nocssboard.repository.post.dto.PostRepositoryDto
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable


class PostQueryDslImpl(
        private val queryFactory: JPAQueryFactory
) : PostQueryDsl {
    override fun findAllBySearch(
            search: PostRepositoryDto.FindAllBySearch.SearchCondition?, order: PostRepositoryDto.FindAllBySearch.OrderCondition, pageable: Pageable): Page<Post> {
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
                .selectFrom(post)
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