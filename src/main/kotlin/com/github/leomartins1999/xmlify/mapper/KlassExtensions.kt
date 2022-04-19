package com.github.leomartins1999.xmlify.mapper

import com.github.leomartins1999.xmlify.mapper.annotations.XMLName
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation

internal fun KClass<*>.getName() =
    findAnnotation<XMLName>()
        ?.name
        ?: simpleName!!.replaceFirstChar { it.lowercase() }
