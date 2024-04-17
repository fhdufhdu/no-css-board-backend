package com.fhdufhdu.nocssboard.domain.board.controller.dto

import jakarta.validation.constraints.NotBlank

class PostAdditionRequest (
    @field:NotBlank
    val title: String,
    @field:NotBlank
    val content: String,
)