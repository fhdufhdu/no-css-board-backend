package com.fhdufhdu.nocssboard.common.dto

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import java.lang.annotation.Inherited

@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
@Inherited
annotation class JsonSnake()
