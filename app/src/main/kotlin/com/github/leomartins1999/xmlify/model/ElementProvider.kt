package com.github.leomartins1999.xmlify.model

import com.github.leomartins1999.xmlify.AppConfiguration
import com.github.leomartins1999.xmlify.utils.instanceClassWithName

interface ElementProvider {
    val element: Element
}

fun getRootElement(config: AppConfiguration = AppConfiguration()) =
    if (config.elementProviderClass == null) defaultElement
    else config.elementProviderClass
        .instanceClassWithName<ElementProvider>()
        .element

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
