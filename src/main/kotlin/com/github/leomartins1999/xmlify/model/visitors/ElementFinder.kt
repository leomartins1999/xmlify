package com.github.leomartins1999.xmlify.model.visitors

import com.github.leomartins1999.xmlify.model.Element
import com.github.leomartins1999.xmlify.model.ElementPredicate
import com.github.leomartins1999.xmlify.model.LeafElement
import com.github.leomartins1999.xmlify.model.TreeElement

internal class ElementFinder(
    val element: Element,
    val predicate: ElementPredicate
) : ElementVisitor {

    private val results = mutableListOf<Element>()

    fun find() = element.accept(this).let { results }

    override fun visit(element: LeafElement) = checkAndAdd(element)

    override fun visit(element: TreeElement) = checkAndAdd(element).let { true }

    private fun checkAndAdd(element: Element) {
        if (predicate(element)) results.add(element)
    }
}
