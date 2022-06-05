package com.github.leomartins1999.xmlify.utils

import kotlin.reflect.full.createInstance

fun <T> String.instanceClassWithName(): T {
    val providerClass = Class.forName(this).kotlin
    return providerClass.createInstance() as T
}
