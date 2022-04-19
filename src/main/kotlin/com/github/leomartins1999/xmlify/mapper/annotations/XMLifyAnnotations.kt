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
