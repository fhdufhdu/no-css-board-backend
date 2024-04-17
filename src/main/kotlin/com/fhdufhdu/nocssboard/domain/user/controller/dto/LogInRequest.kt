package com.fhdufhdu.nocssboard.domain.user.controller.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

class LogInRequest(
    @NotNull
    @NotEmpty
    val id: String,

    @NotNull
    @NotEmpty
    @Size(min=3)
    val password: String,
)