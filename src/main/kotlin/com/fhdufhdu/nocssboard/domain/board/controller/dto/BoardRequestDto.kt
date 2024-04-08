package com.fhdufhdu.nocssboard.domain.board.controller.dto

import com.fhdufhdu.nocssboard.common.dto.CommonPaginationDto
import com.fhdufhdu.nocssboard.common.dto.SortDirection
import com.fhdufhdu.nocssboard.common.dto.ValidEnum
import com.fhdufhdu.nocssboard.domain.board.service.dto.FindPostsEnum
import jakarta.validation.constraints.NotBlank

class BoardRequestDto {

    class GetPosts(
        @field:NotBlank
        @field:ValidEnum(enumClass = FindPostsEnum.Input.SortCriteria::class, valuePropertyName = "value")
        private val sort_criteria: String,

        @field:NotBlank
        @field:ValidEnum(enumClass = SortDirection::class, valuePropertyName = "value")
        private val sort_direction: String,

        private val search_query: String? = null,

        @field:ValidEnum(enumClass = FindPostsEnum.Input.SearchCriteria::class, valuePropertyName = "value", isNull = true)
        private val search_criteria: String? = null,

        page_number: Int,
        page_size: Int,
    ) : CommonPaginationDto(page_number, page_size) {

        val sortCriteria: String
            get() {
                return sort_criteria
            }

        val sortDirection: String
            get() {
                return sort_direction
            }

        val searchQuery: String?
            get(){
                return search_query
            }

        val searchCriteria: String?
            get() {
                return search_criteria
            }
    }

}