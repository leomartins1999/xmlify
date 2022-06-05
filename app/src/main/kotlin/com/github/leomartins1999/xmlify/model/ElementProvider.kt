package com.github.leomartins1999.xmlify.model

import com.github.leomartins1999.xmlify.AppConfiguration
import kotlin.reflect.full.createInstance

interface ElementProvider {
    val element: Element
}

fun getRootElement(config: AppConfiguration): Element {
    if (config.elementProviderClass == null) return defaultElement

    val providerClass = Class.forName(config.elementProviderClass).kotlin
    val instance = providerClass.createInstance() as ElementProvider

    return instance.element
}

val defaultElement = element(
    name = "myElement",
    attributes = mapOf("attr1" to "value1", "attr2" to "value2"),
    children = listOf(
        element("null"),
        element("string", "xpto"),
        element(
            "tree",
            children = listOf(
                element("leaf1", "l"),
                element("leaf2", "l")
            )
        )
    )
)
