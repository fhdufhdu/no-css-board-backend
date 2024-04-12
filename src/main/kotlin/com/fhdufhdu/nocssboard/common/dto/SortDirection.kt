package com.fhdufhdu.nocssboard.common.dto

import com.querydsl.core.types.Order
import org.springframework.data.domain.Sort

enum class SortDirection(val value: String, val jpaDirection: Sort.Direction, val queryDslOrder: Order) {
    ASC("ASC", Sort.Direction.ASC, Order.ASC), DESC("DESC", Sort.Direction.DESC, Order.DESC);

    companion object {
        fun from(direction: String): SortDirection {
            return entries.first {
                it.value == direction
            }
        }
    }
}