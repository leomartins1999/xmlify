package com.github.leomartins1999.xmlify.model.visitors

import com.github.leomartins1999.xmlify.model.Element
import com.github.leomartins1999.xmlify.model.LeafElement
import com.github.leomartins1999.xmlify.model.LeafPredicate
import com.github.leomartins1999.xmlify.model.TreeElement
import com.github.leomartins1999.xmlify.model.TreePredicate

internal class ElementFilter(
    private val root: TreeElement,
    private val leafPredicate: LeafPredicate = { true },
    private val treePredicate: TreePredicate = { true }
) : ElementVisitor {

    private val elements = mutableListOf<Element>()

    fun filter() =
        root.also { it.children.forEach { children -> children.accept(this) } }
            .copy(children = elements)

    override fun visit(element: LeafElement) {
        if (leafPredicate(element)) elements.add(element)
    }

    override fun visit(element: TreeElement): Boolean {
        if (treePredicate(element)) {
            ElementFilter(element, leafPredicate, treePredicate)
                .filter()
                .let { elements.add(it) }
        }

        return false
    }
}
