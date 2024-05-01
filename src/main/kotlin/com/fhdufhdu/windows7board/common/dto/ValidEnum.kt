package com.fhdufhdu.windows7board.common.dto

import com.fhdufhdu.windows7board.common.dto.ValidEnum.EnumValidator
import jakarta.validation.Constraint
import jakarta.validation.ConstraintValidator
import jakarta.validation.ConstraintValidatorContext
import jakarta.validation.Payload
import kotlin.reflect.KClass

@Target(
    AnnotationTarget.FUNCTION,
    AnnotationTarget.PROPERTY_GETTER,
    AnnotationTarget.PROPERTY_SETTER,
    AnnotationTarget.FIELD,
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.CONSTRUCTOR,
    AnnotationTarget.VALUE_PARAMETER,
    AnnotationTarget.CLASS,
    AnnotationTarget.TYPE,
    AnnotationTarget.TYPE_PARAMETER
)
@Retention(
    AnnotationRetention.RUNTIME
)
@Constraint(validatedBy = [EnumValidator::class])
annotation class ValidEnum(
    val enum: KClass<out Enum<*>>,
    val isNull: Boolean = false,
    val message: String = "허용되지 않은 값입니다.",
    val groups: Array<KClass<Any>> = [],
    val payload: Array<KClass<Payload>> = [],
    val ignoreCase: Boolean = false,
) {
    class EnumValidator(
    ) : ConstraintValidator<ValidEnum, String> {
        private lateinit var annotation: ValidEnum

        override fun initialize(constraintAnnotation: ValidEnum) {
            this.annotation = constraintAnnotation
        }

        override fun isValid(value: String?, context: ConstraintValidatorContext): Boolean {
            val enums = annotation.enum.java.enumConstants
            if (value == null && annotation.isNull) {
                return true
            }

            return enums.any { it.name == value }
        }
    }
}
