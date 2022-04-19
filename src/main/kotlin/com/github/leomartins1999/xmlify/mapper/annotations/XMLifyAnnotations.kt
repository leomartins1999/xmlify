package com.github.leomartins1999.xmlify.mapper.annotations

@Target(AnnotationTarget.CLASS, AnnotationTarget.PROPERTY)
annotation class XMLName(val name: String)

annotation class XMLIgnore
