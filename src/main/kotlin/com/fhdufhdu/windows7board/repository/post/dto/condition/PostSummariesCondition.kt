package com.fhdufhdu.windows7board.repository.post.dto.condition

import com.fhdufhdu.windows7board.entity.QPost
import com.fhdufhdu.windows7board.repository.post.dto.condition.PostSummariesCondition.OrderCriteria.*
import com.querydsl.core.types.Order as DslOrder
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import com.querydsl.core.types.dsl.Expressions
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException

class PostSummariesCondition(
    val search: Search?,
    val order: Order
) {
    class Search(
        val searchQuery: String,
        val searchCriteria: SearchCriteria
    )

    class Order(
        val order: DslOrder,
        val orderCriteria: OrderCriteria,
    )

    enum class OrderCriteria(val value: String) {
        TITLE("title"),
        CONTENT("content"),
        CREATED_AT("created_at"),
        USER_ID("user_id");
    }

    enum class SearchCriteria(val value: String) {
        TITLE("title"),
        CONTENT("content"),
        USER_ID("user_id"),
        ALL("all");

    }

}