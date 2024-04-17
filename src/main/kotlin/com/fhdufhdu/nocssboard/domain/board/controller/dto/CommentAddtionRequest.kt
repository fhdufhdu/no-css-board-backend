package com.fhdufhdu.nocssboard.domain.board.controller.dto

import jakarta.validation.constraints.NotBlank

class CommentAddtionRequest (
    @field:NotBlank
    val content: String,
)