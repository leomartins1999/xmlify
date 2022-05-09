package com.github.leomartins1999.xmlify.model

import com.github.leomartins1999.xmlify.model.visitors.ElementFilter
import com.github.leomartins1999.xmlify.model.visitors.ElementFinder
import com.github.leomartins1999.xmlify.model.visitors.ElementRenderer
import com.github.leomartins1999.xmlify.model.visitors.ElementVisitor

typealias ElementPredicate = (Element) -> Boolean
typealias LeafPredicate = (LeafElement) -> Boolean
typealias TreePredicate = (TreeElement) -> Boolean

/**
 * Base type for XML elements
 */
abstract class Element(val name: String, val attributes: Map<String, String>) {

    /**
     * Accepts a visitor
     */
    abstract fun accept(visitor: ElementVisitor)

    /**
     * Renders the element as a String
     */
    fun render() = ElementRenderer(this).render()

    /**
     * Copies the element to another instance
     * Name and attributes can be redefined
     * This is being added as it is useful in the
     * mapper module
     */
    internal abstract fun copyElement(
        name: String? = null,
        attributes: Map<String, String>? = null
    ): Element
}

/**
 * Type for XML elements which contain a value
 */
data class LeafElement internal constructor(
    private val elementName: String,
    val value: Any? = null,
    private val elementAttributes: Map<String, String> = mapOf()
) : Element(elementName, elementAttributes) {
    override fun accept(visitor: ElementVisitor): Unit = visitor.visit(this)
    override fun copyElement(name: String?, attributes: Map<String, String>?) =
        copy(
            elementName = name ?: elementName,
            elementAttributes = attributes ?: elementAttributes
        )
}

/**
 * Type for XML elements which contain leaf elements
 */
data class TreeElement internal constructor(
    private val elementName: String,
    val children: List<Element> = emptyList(),
    private val elementAttributes: Map<String, String> = mapOf()
) : Element(elementName, elementAttributes) {
    fun hasChildren() = children.isNotEmpty()

    /**
     * Finds nested elements that match the given predicate
     * Also returns elements nested inside children elements (children inside children elements)
     */
    fun find(filter: ElementPredicate) = ElementFinder(this, filter).find()

    /**
     * Filters nested elements by the given predicates
     * Also filters elements nested inside children elements (children inside children elements)
     */
    fun filter(
        leafPredicate: LeafPredicate = { true },
        treePredicate: TreePredicate = { true }
    ) = ElementFilter(this, leafPredicate, treePredicate).filter()

    override fun accept(visitor: ElementVisitor) {
        if (!visitor.visit(this)) return

        children.forEach { it.accept(visitor) }

        visitor.endVisit(this)
    }

    override fun copyElement(name: String?, attributes: Map<String, String>?) =
        copy(
            elementName = name ?: elementName,
            elementAttributes = attributes ?: elementAttributes
        )
}
