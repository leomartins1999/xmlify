package com.github.leomartins1999.xmlify.mapper.annotations

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

internal fun List<XMLAttribute>.toAttributes() = associateBy({ it.key }, { it.value })
