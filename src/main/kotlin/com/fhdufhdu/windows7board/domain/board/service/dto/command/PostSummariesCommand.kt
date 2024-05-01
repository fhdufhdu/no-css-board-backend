package com.fhdufhdu.windows7board.domain.board.service.dto.command

import com.fhdufhdu.windows7board.common.dto.SortDirection
import com.fhdufhdu.windows7board.domain.board.controller.dto.PostSummariesRequest

class PostSummariesCommand(
    val page: Page,
    val sort: Sort,
    val search: Search? = null,
) {
    class Page(
        val number: Int,
        val size: Int
    )

    class Sort(val criteria: SortCriteria, val direction: SortDirection)

    class Search(
        val searchQuery: String,
        val searchCriteria: SearchCriteria,
    )

    enum class SortCriteria {
        TITLE, CONTENT, CREATED_AT, USER_ID
    }

    enum class SearchCriteria {
        TITLE, CONTENT, USER_ID
    }
}