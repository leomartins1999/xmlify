package com.github.leomartins1999.xmlify.model

class Model(
    private val root: Element = defaultElement
) {

    val element: Element
        get() = root

    private companion object {
        private val defaultElement = element(
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
    }
}
