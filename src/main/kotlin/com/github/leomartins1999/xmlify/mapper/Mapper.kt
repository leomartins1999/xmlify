package com.github.leomartins1999.xmlify.mapper

import com.github.leomartins1999.xmlify.mapper.strategies.CollectionMappingStrategy
import com.github.leomartins1999.xmlify.mapper.strategies.EnumMappingStrategy
import com.github.leomartins1999.xmlify.mapper.strategies.ObjectMappingStrategy
import com.github.leomartins1999.xmlify.mapper.strategies.ValueMappingStrategy
import com.github.leomartins1999.xmlify.model.Element
import com.github.leomartins1999.xmlify.model.element
import kotlin.reflect.KClass
import kotlin.reflect.full.isSubclassOf

private val nullElement = element("null")

internal fun toElement(instance: Any?): Element {
    if (instance == null) return nullElement

    return selectStrategy(instance).toElement(instance)
}

private fun selectStrategy(instance: Any) = when {
    isValueType(instance::class) -> ValueMappingStrategy
    isCollection(instance::class) -> CollectionMappingStrategy
    isEnum(instance::class) -> EnumMappingStrategy
    else -> ObjectMappingStrategy
}

private fun isValueType(type: KClass<*>) =
    type.isSubclassOf(Number::class) || type == String::class || type == Boolean::class

private fun isCollection(type: KClass<*>) = type.isSubclassOf(Collection::class)

private fun isEnum(type: KClass<*>) = type.isSubclassOf(Enum::class)
