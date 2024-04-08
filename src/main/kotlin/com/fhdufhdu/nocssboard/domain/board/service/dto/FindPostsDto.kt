package com.fhdufhdu.nocssboard.domain.board.service.dto

import com.fhdufhdu.nocssboard.common.dto.SortDirection
import java.util.Date

class FindPostsDto {
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
            val criteria = FindPostsEnum.Input.SortCriteria.from(criteria)
            val direction = SortDirection.from(direction)
        }

        class Search(
            val searchQuery: String,
            searchCriteria: String,
        ){
            val searchCriteria = FindPostsEnum.Input.SearchCriteria.from(searchCriteria)
        }
    }

    class Return(
        val posts: List<Post>,
        val number: Int,
        val totalPages: Int,
    ) {
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