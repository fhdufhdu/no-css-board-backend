package com.fhdufhdu.windows7board.common.entity

import com.fhdufhdu.windows7board.entity.Post
import jakarta.persistence.AttributeConverter
import kotlin.reflect.KClass

open class CommonEnumConverter<E: Enum<*>>(private val enumClass: KClass<E>): AttributeConverter<E, String> {
    override fun convertToDatabaseColumn(attribute: E): String = attribute.name
    override fun convertToEntityAttribute(dbData: String): E = enumClass.java.enumConstants.first{ it.name == dbData }

}