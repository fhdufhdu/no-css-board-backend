package com.fhdufhdu.windows7board.domain.board.service.dto.command

import com.fhdufhdu.windows7board.common.dto.SortDirection

class PostSummariesCommand(
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
        TITLE("title"),
        CONTENT("content"),
        USER_ID("user_id");

        companion object {
            fun from(s: String): SearchCriteria {
                return SearchCriteria.entries.first { it.value == s }
            }
        }
    }
}