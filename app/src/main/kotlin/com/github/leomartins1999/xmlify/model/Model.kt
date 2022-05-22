package com.github.leomartins1999.xmlify.model

class Model(
    private val root: Element = defaultElement
) {

    val element: Element
        get() = root

    private companion object {
        private val defaultElement = element("myElement")
    }

}
