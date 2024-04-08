package com.fhdufhdu.nocssboard.common.dto

import org.springframework.data.domain.Sort

enum class SortDirection(val value: String) {
    ASC("ASC"), DESC("DESC");

    fun toJpaDirection(): Sort.Direction{
        if (this == SortDirection.ASC){
            return Sort.Direction.ASC
        }
        return Sort.Direction.DESC
    }

    companion object{
        fun from(direction: String):SortDirection {
            return entries.first {
                it.value == direction
            }
        }
    }
}