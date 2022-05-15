package com.github.leomartins1999.xmlify.mapper.annotations

import com.github.leomartins1999.xmlify.mapper.strategies.MappingStrategy
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

/**
 * Overrides Element names
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY)
annotation class XMLName(val name: String)

/**
 * Hides nested Elements
 */
@Target(AnnotationTarget.PROPERTY)
annotation class XMLIgnore

/**
 * Adds an attribute to elements
 * (works with classes and properties)
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY)
@Repeatable
annotation class XMLAttribute(val key: String, val value: String)

/**
 * Defines a mapper class for the class/property annotated
 * This mapper class must:
 * - implement the MappingStrategy interface
 * - have a primary constructor with no arguments
 * This mapper will have precedence over all other mapping
 * strategies
 */
@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY)
annotation class XMLMapper(val strategyClass: KClass<out MappingStrategy>)

internal fun List<XMLAttribute>.toAttributes() = associateBy({ it.key }, { it.value })

internal fun KClass<out MappingStrategy>.instantiateMapper() = primaryConstructor!!.call()
