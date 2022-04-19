package com.github.leomartins1999.xmlify.mapper

import kotlin.reflect.KClass

internal fun KClass<*>.getName() = simpleName!!.replaceFirstChar { it.lowercase() }
