package com.github.leomartins1999.xmlify.model

class Model(
    private val root: Element = defaultElement
) {

    val element: Element
        get() = root

    private companion object {
        private val defaultElement = element(
            name = "myElement",
            attributes = mapOf(
                "key1" to "value1",
                "key2" to "value2"
            )
        )
    }

}
