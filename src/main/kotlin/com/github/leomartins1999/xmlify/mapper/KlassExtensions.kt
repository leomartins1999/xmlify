package com.github.leomartins1999.xmlify.mapper

import com.github.leomartins1999.xmlify.mapper.annotations.XMLAttribute
import com.github.leomartins1999.xmlify.mapper.annotations.XMLName
import com.github.leomartins1999.xmlify.mapper.annotations.toAttributes
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.findAnnotations

internal fun KClass<*>.getName() =
    findAnnotation<XMLName>()
        ?.name
        ?: simpleName!!.replaceFirstChar { it.lowercase() }

@OptIn(ExperimentalStdlibApi::class)
internal fun KClass<*>.getAttributes() = findAnnotations(XMLAttribute::class).toAttributes()
