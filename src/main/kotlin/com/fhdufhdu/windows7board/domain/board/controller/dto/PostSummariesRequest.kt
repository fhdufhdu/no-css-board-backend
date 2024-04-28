package com.fhdufhdu.windows7board.domain.board.controller.dto

import com.fhdufhdu.windows7board.common.dto.CommonPaginationDto
import com.fhdufhdu.windows7board.common.dto.ValidEnum
import jakarta.validation.constraints.NotBlank

class PostSummariesRequest(
    @field:NotBlank
    @field:ValidEnum(values=["title", "content", "created_at", "user_id" ])
    private val sort_criteria: String,

    @field:NotBlank
    @field:ValidEnum(values=["ASC", "DESC"])
    private val sort_direction: String,

    private val search_query: String? = null,

    @field:ValidEnum(values=["title", "content", "user_id"], isNull = true)
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