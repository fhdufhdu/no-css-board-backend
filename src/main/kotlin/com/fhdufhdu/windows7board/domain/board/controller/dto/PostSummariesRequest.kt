package com.fhdufhdu.windows7board.domain.board.controller.dto

import com.fhdufhdu.windows7board.common.dto.CommonPaginationDto
import com.fhdufhdu.windows7board.common.dto.SortDirection
import com.fhdufhdu.windows7board.common.dto.ValidEnum
import jakarta.validation.constraints.NotBlank
import org.springframework.data.domain.Pageable

class PostSummariesRequest(
    @field:ValidEnum(enum = SortCriteria::class)
    private val sort_criteria: String = SortCriteria.CREATED_AT.name,

    @field:ValidEnum(enum = SortDirection::class)
    private val sort_direction: String = SortDirection.DESC.name,

    private val search_query: String? = null,

    @field:ValidEnum(enum = SearchCriteria::class)
    private val search_criteria: String = SearchCriteria.TITLE.name,

    page_number: Int,
    page_size: Int,
) : CommonPaginationDto.Request(page_number, page_size) {

    val sortCriteria: SortCriteria
        get() {
            return SortCriteria.valueOf(sort_criteria)
        }

    val sortDirection: SortDirection
        get() {
            return SortDirection.valueOf(sort_direction)
        }

    val searchQuery: String?
        get() {
            return search_query
        }

    val searchCriteria: SearchCriteria
        get() {
            return SearchCriteria.valueOf(search_criteria)
        }

    enum class SortCriteria {
        TITLE, CONTENT, CREATED_AT, USER_ID
    }
    enum class SearchCriteria {
        TITLE, CONTENT, USER_ID
    }
}