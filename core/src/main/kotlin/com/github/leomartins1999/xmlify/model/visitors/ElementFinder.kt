package com.github.leomartins1999.xmlify.model.visitors

import com.github.leomartins1999.xmlify.model.Element
import com.github.leomartins1999.xmlify.model.LeafElement
import com.github.leomartins1999.xmlify.model.LeafPredicate
import com.github.leomartins1999.xmlify.model.TreeElement
import com.github.leomartins1999.xmlify.model.TreePredicate

internal class ElementFinder(
    val element: Element,
    val leafPredicate: LeafPredicate,
    val treePredicate: TreePredicate
) : ElementVisitor {

    private val results = mutableListOf<Element>()

    fun find() = element.accept(this).let { results }

    override fun visit(element: LeafElement) {
        if (leafPredicate(element)) results.add(element)
    }

    override fun visit(element: TreeElement): Boolean {
        if (treePredicate(element)) results.add(element)

        return true
    }
}
