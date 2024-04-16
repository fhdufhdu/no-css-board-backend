package com.fhdufhdu.nocssboard.repository.post.dto

import com.fhdufhdu.nocssboard.entity.QPost
import com.querydsl.core.types.Order
import com.querydsl.core.types.OrderSpecifier
import com.querydsl.core.types.dsl.BooleanExpression
import org.springframework.http.HttpStatus
import org.springframework.web.server.ResponseStatusException
import java.sql.Timestamp

class PostRepositoryDto {
    class FindAllBySearch {
        class Posts (
            val id: Long,
            val userId: String,
            val title: String,
            val content: String,
            val createdAt: Timestamp,
            val updatedAt: Timestamp?
        )
        class SearchCondition(
                val searchQuery: String,
                val searchCriteria: String
        )

        class OrderCondition(
                val order: Order,
                val orderCriteria: OrderCriteria
        )

        enum class OrderCriteria(val value: String) {
            TITLE("title") {
                override fun getOrderSpecifier(order: Order): OrderSpecifier<*> {
                    return OrderSpecifier(order, QPost.post.title)
                }
            },
            CONTENT("content"){
                override fun getOrderSpecifier(order: Order): OrderSpecifier<*> {
                    return OrderSpecifier(order, QPost.post.content)
                }
                               },
            CREATED_AT("created_at"){
                override fun getOrderSpecifier(order: Order): OrderSpecifier<*> {
                    return OrderSpecifier(order, QPost.post.createdAt)
                }
                                    },
            USER_ID("user_id"){
                override fun getOrderSpecifier(order: Order): OrderSpecifier<*> {
                    return OrderSpecifier(order, QPost.post.user.id)
                }
            };

            abstract fun getOrderSpecifier(order: Order): OrderSpecifier<*>;

            companion object {
                fun from(searchCriteria: String?): OrderCriteria {
                    return entries.find { it.value == searchCriteria }
                            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 ENUM 값입니다.")

                }
            }
        }

        enum class SearchCriteria(val value: String) {
            TITLE("title") {
                override fun getLikeExpression(searchQuery: String): BooleanExpression {
                    return likeTitle(searchQuery)

                }
            },
            CONTENT("content") {
                override fun getLikeExpression(searchQuery: String): BooleanExpression {
                    return likeContent(searchQuery)
                }
            },
            USER_ID("user_id") {
                override fun getLikeExpression(searchQuery: String): BooleanExpression {
                    return likeUserId(searchQuery)
                }
            },
            ALL("all") {
                override fun getLikeExpression(searchQuery: String): BooleanExpression {
                    return likeTitle(searchQuery).or(likeContent(searchQuery)).or(likeUserId(searchQuery))
                }
            };

            abstract fun getLikeExpression(searchQuery: String): BooleanExpression;
            private fun searchToLikeQuery(searchQuery: String): String {
                return "%$searchQuery%"
            }

            protected fun likeTitle(searchQuery: String): BooleanExpression {
                return QPost.post.title.like(searchToLikeQuery(searchQuery))
            }

            protected fun likeContent(searchQuery: String): BooleanExpression {
                return QPost.post.content.like(searchToLikeQuery(searchQuery))
            }

            protected fun likeUserId(searchQuery: String): BooleanExpression {
                return QPost.post.user.id.like(searchToLikeQuery(searchQuery))
            }

            companion object {
                fun from(searchCriteria: String?): SearchCriteria {
                    return entries.find { it.value == searchCriteria }
                            ?: throw ResponseStatusException(HttpStatus.BAD_REQUEST, "잘못된 ENUM 값입니다.")

                }
            }
        }

    }
}