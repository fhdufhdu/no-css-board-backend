package com.fhdufhdu.nocssboard.repository.post.dto.condition

import com.fhdufhdu.nocssboard.entity.QPost
import com.fhdufhdu.nocssboard.repository.post.dto.condition.PostSummariesCondition.OrderCriteria.*
import com.querydsl.core.types.Order as DslOrder
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class PostSummariesCondition(
    val search: Search,
    val order: Order
) {
    class Search(
        val searchQuery: String?,
        private val _searchCriteria: String?
    ) {
        val searchCriteria: SearchCriteria?
            get() {
                if (_searchCriteria == null) return null
                return SearchCriteria.from(_searchCriteria)
            }

        val searchExpression: BooleanExpression
            get() {
                if (searchQuery == null) return Expressions.TRUE
                val query = searchToLikeQuery(searchQuery)
                return when (searchCriteria) {
                    SearchCriteria.TITLE -> likeTitle(query)
                    SearchCriteria.CONTENT -> likeContent(query)
                    SearchCriteria.USER_ID -> likeUserId(query)
                    null, SearchCriteria.ALL -> likeTitle(query).or(likeContent(query)).or(likeUserId(query))
                }

            }

        private fun searchToLikeQuery(searchQuery: String): String {
            return "%$searchQuery%"
        }

        private fun likeTitle(searchQuery: String): BooleanExpression {
            return QPost.post.title.like(searchToLikeQuery(searchQuery))
        }

        private fun likeContent(searchQuery: String): BooleanExpression {
            return QPost.post.content.like(searchToLikeQuery(searchQuery))
        }

        private fun likeUserId(searchQuery: String): BooleanExpression {
            return QPost.post.user.id.like(searchToLikeQuery(searchQuery))
        }
    }

    class Order(
        private val order: DslOrder,
        orderCriteria: String,
    ) {
        private val orderCriteria = OrderCriteria.from(orderCriteria)
        val orderSpecifier: OrderSpecifier<*>
            get() {
                return when (orderCriteria) {
                    TITLE -> OrderSpecifier(order, QPost.post.title)
                    CONTENT -> OrderSpecifier(order, QPost.post.content)
                    CREATED_AT -> OrderSpecifier(order, QPost.post.createdAt)
                    USER_ID -> OrderSpecifier(order, QPost.post.user.id)
                }
            }
    }

    enum class OrderCriteria(val value: String) {
        TITLE("title"),
        CONTENT("content"),
        CREATED_AT("created_at"),
        USER_ID("user_id");

        companion object {
            fun from(searchCriteria: String?): OrderCriteria {
                return entries.find { it.value == searchCriteria }
                    ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "${javaClass.name}-잘못된 ENUM 값입니다.")

            }
        }
    }

    enum class SearchCriteria(val value: String) {
        TITLE("title"),
        CONTENT("content"),
        USER_ID("user_id"),
        ALL("all");


        companion object {
            fun from(searchCriteria: String?): SearchCriteria {
                return entries.find { it.value == searchCriteria }
                    ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "${javaClass.name}-잘못된 ENUM 값입니다.")

            }
        }
    }

}