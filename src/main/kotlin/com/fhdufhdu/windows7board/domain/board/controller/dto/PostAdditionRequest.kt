package com.fhdufhdu.windows7board.domain.board.controller.dto

import jakarta.validation.constraints.NotBlank

class PostAdditionRequest (
    @field:NotBlank
    val title: String,
    @field:NotBlank
    val content: String,
)