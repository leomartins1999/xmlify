package com.github.leomartins1999.htmleditor

import com.github.leomartins1999.xmlify.model.ElementProvider
import com.github.leomartins1999.xmlify.model.element

class HTMLElementProvider : ElementProvider {

    override val element = element(
        name = "html",
        attributes = mapOf("lang" to "EN"),
        children = listOf(
            element(
                name = "head",
                children = listOf(
                    element(name = "title", value = "Some HTML App!")
                )
            ),
            element(
                name = "body",
                children = listOf(
                    element(
                        name = "div",
                        children = listOf(),
                        attributes = mapOf("id" to "root")
                    )
                )
            )
        )
    )
}
