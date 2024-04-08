package com.fhdufhdu.nocssboard.domain.user.service.dto

import org.jetbrains.annotations.NotNull

class ExistUserId {
    class Return(
        @NotNull
        val exist: Boolean
    )
}