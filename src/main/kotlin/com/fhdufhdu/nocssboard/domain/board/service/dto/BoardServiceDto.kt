package com.fhdufhdu.nocssboard.domain.board.service.dto

import com.fhdufhdu.nocssboard.common.dto.CommonPaginationDto
import com.fhdufhdu.nocssboard.common.dto.SortDirection
import io.swagger.v3.oas.annotations.media.Schema
import java.util.*

class BoardServiceDto {
    class FindPosts {
        class Input(
                val page: Page,
                val sort: Sort,
                val search: Search? = null,
        ) {
            class Page(
                    val number: Int,
                    val size: Int
            )

            class Sort(criteria: String, direction: String) {
                val criteria = SortCriteria.from(criteria)
                val direction = SortDirection.from(direction)
            }

            class Search(
                    val searchQuery: String,
                    searchCriteria: String,
            ) {
                val searchCriteria = SearchCriteria.from(searchCriteria)
            }

            enum class SortCriteria(val value: String) {
                TITLE("title"), CONTENT("content"), CREATED_AT("created_at"), USER_ID("user_id");

                companion object {
                    fun from(s: String): SortCriteria {
                        return SortCriteria.entries.first { it.value == s }
                    }

                }
            }

            enum class SearchCriteria(val value: String) {
                TITLE("title"), CONTENT("content"), USER_ID("user_id");

                companion object {
                    fun from(s: String): SearchCriteria {
                        return SearchCriteria.entries.first { it.value == s }
                    }
                }
            }
        }


        @Schema(name = "FindPosts_Return")
        class Return(
                val posts: List<Post>,
                number: Int,
                totalPages: Int,
        ):CommonPaginationDto.Response(number, totalPages) {
            class Post(
                    val id: Long,
                    val userId: String,
                    val title: String,
                    val content: String,
                    val createdAt: Date,
                    val updatedAt: Date?
            )
        }
    }

    class FindOnePost {
        @Schema(name="FindOnePost_Return")
        class Return(
                val id: Long,
                val userId: String,
                val title: String,
                val content: String,
                val createdAt: Date,
                val updatedAt: Date?
        )
    }

    class FindComments {
        @Schema(name="FindComments_Return")
        class Return(
                val comments: List<Comment>,
                number: Int,
                totalPages: Int,
        ):CommonPaginationDto.Response(number, totalPages){
            class Comment(
                    val id: Long,
                    val userId: String,
                    val content:String,
                    val createdAt: Date,
                    val updatedAt: Date?
            )
        }
    }
}