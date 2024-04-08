package com.fhdufhdu.nocssboard.domain.board.service.dto

import com.fhdufhdu.nocssboard.common.dto.SortDirection

class FindPostsEnum {
    class Input {
        enum class SortCriteria(val value: String) {
            TITLE("title"), CONTENT("content"), CREATED_AT("created_at"), USER_ID("user_id");

            companion object{
                fun from(s: String): SortCriteria {
                    return SortCriteria.entries.first { it.value == s }
                }

            }
        }

        enum class SearchCriteria(val value: String) {
            TITLE("title"), CONTENT("content"), USER_ID("user_id");

            companion object {
                fun from(s: String):SearchCriteria {
                    return SearchCriteria.entries.first{ it.value == s }
                }
            }
        }

    }
}