package com.fhdufhdu.windows7board.common.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

class CommonPaginationDto {
    abstract class Request(
            @field:NotNull
            @field:Min(0)
            private val page_number: Int,

            @field:NotNull
            @field:Min(1)
            private val page_size: Int) {

        val pageNumber: Int
            get() {
                return page_number
            }

        val pageSize: Int
            get() {
                return page_size
            }
    }

    abstract class Response(
            val number: Int,
            val totalPages: Int,
    )
}