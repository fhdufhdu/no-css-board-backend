package com.fhdufhdu.windows7board.domain.board.controller.dto

import jakarta.validation.constraints.NotBlank

class CommentAddtionRequest (
    @field:NotBlank
    val content: String,
)