package com.fhdufhdu.nocssboard.domain.board.controller.dto

import com.fhdufhdu.nocssboard.common.dto.CommonPaginationDto
import com.fhdufhdu.nocssboard.common.dto.SortDirection
import com.fhdufhdu.nocssboard.common.dto.ValidEnum
import com.fhdufhdu.nocssboard.domain.board.service.dto.BoardServiceDto
import io.swagger.v3.oas.annotations.Hidden
import jakarta.validation.constraints.NotBlank

class BoardRequestDto {

    class FindPosts(
            @field:NotBlank
            @field:ValidEnum(enumClass = BoardServiceDto.FindPosts.Input.SortCriteria::class, valuePropertyName = "value")
            private val sort_criteria: String,

            @field:NotBlank
            @field:ValidEnum(enumClass = SortDirection::class, valuePropertyName = "value")
            private val sort_direction: String,

            private val search_query: String? = null,

            @field:ValidEnum(enumClass = BoardServiceDto.FindPosts.Input.SearchCriteria::class, valuePropertyName = "value", isNull = true)
            private val search_criteria: String? = null,

            page_number: Int,
            page_size: Int,
    ) : CommonPaginationDto.Request(page_number, page_size) {

        val sortCriteria: String
            get() {
                return sort_criteria
            }

        val sortDirection: String
            get() {
                return sort_direction
            }

        val searchQuery: String?
            get() {
                return search_query
            }

        val searchCriteria: String?
            get() {
                return search_criteria
            }
    }

    class AddPost(
            @field:NotBlank
            val title: String,
            @field:NotBlank
            val content: String,
    )

    class FindComments(
            page_number: Int,
            page_size: Int
    ): CommonPaginationDto.Request(page_number, page_size)

    class AddComment(
            @field:NotBlank
            val content: String,
    )

}