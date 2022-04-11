package com.github.leomartins1999.xmlify.model.visitors

import com.github.leomartins1999.xmlify.model.Element
import com.github.leomartins1999.xmlify.model.LeafElement
import com.github.leomartins1999.xmlify.model.TreeElement

class ElementFinder(
    val element: Element,
    val filter: (Element) -> Boolean
) : ElementVisitor {

    private val results = mutableListOf<Element>()

    fun find() = element.accept(this).let { results }

    override fun visit(element: LeafElement) = checkAndAdd(element)

    override fun visit(element: TreeElement) = checkAndAdd(element).let { true }

    private fun checkAndAdd(element: Element) {
        if (filter(element)) results.add(element)
    }
}
