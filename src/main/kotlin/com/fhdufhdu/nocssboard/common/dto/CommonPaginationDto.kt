package com.fhdufhdu.nocssboard.common.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

abstract class CommonPaginationDto(
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