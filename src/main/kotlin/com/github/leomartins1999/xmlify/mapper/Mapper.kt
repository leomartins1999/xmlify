package com.github.leomartins1999.xmlify.mapper

import com.github.leomartins1999.xmlify.mapper.annotations.XMLMapper
import com.github.leomartins1999.xmlify.mapper.strategies.CollectionMappingStrategy
import com.github.leomartins1999.xmlify.mapper.strategies.CustomMappingStrategy
import com.github.leomartins1999.xmlify.mapper.strategies.EnumMappingStrategy
import com.github.leomartins1999.xmlify.mapper.strategies.ObjectMappingStrategy
import com.github.leomartins1999.xmlify.mapper.strategies.ValueMappingStrategy
import com.github.leomartins1999.xmlify.model.Element
import com.github.leomartins1999.xmlify.model.element
import kotlin.reflect.KClass
import kotlin.reflect.full.hasAnnotation
import kotlin.reflect.full.isSubclassOf

private val nullElement = element("null")

internal fun toElement(instance: Any?): Element {
    if (instance == null) return nullElement

    val element = selectStrategy(instance).toElement(instance)
    val attributes = instance::class.getAttributes()

    return element.copyElement(attributes = attributes)
}

private fun selectStrategy(instance: Any) = when {
    hasMapper(instance::class) -> CustomMappingStrategy
    isValueType(instance::class) -> ValueMappingStrategy
    isCollection(instance::class) -> CollectionMappingStrategy
    isEnum(instance::class) -> EnumMappingStrategy
    else -> ObjectMappingStrategy
}

private fun isValueType(type: KClass<*>) =
    type.isSubclassOf(Number::class) || type == String::class || type == Boolean::class

private fun isCollection(type: KClass<*>) = type.isSubclassOf(Collection::class)

private fun isEnum(type: KClass<*>) = type.isSubclassOf(Enum::class)

private fun hasMapper(type: KClass<*>) = type.hasAnnotation<XMLMapper>()
