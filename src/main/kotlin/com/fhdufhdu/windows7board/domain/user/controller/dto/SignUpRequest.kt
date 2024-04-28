package com.fhdufhdu.windows7board.domain.user.controller.dto

import jakarta.validation.constraints.NotBlank

class SignUpRequest(
    @NotBlank
    val id: String,

    @NotBlank
    val password: String?,
)