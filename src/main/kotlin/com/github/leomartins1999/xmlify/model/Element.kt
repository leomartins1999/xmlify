package com.github.leomartins1999.xmlify.model

import com.github.leomartins1999.xmlify.model.visitors.ElementFilter
import com.github.leomartins1999.xmlify.model.visitors.ElementFinder
import com.github.leomartins1999.xmlify.model.visitors.ElementVisitor
import com.github.leomartins1999.xmlify.model.visitors.TreeElementRenderer

typealias ElementPredicate = (Element) -> Boolean
typealias LeafPredicate = (LeafElement) -> Boolean
typealias TreePredicate = (TreeElement) -> Boolean

abstract class Element(val name: String, val attributes: Map<String, String>) {
    abstract fun render(): String
    abstract fun accept(visitor: ElementVisitor)
}

data class LeafElement(
    private val elementName: String,
    val value: Any? = null,
    private val elementAttributes: Map<String, String> = mapOf()
) : Element(elementName, elementAttributes) {
    override fun render() = value
        ?.let { leafElementTemplate(this) }
        ?: collapsedElementTemplate(this)

    override fun accept(visitor: ElementVisitor): Unit = visitor.visit(this)
}

data class TreeElement(
    private val elementName: String,
    val children: List<Element> = emptyList(),
    private val elementAttributes: Map<String, String> = mapOf()
) : Element(elementName, elementAttributes) {
    fun hasChildren() = children.isNotEmpty()

    fun find(filter: ElementPredicate) = ElementFinder(this, filter).find()

    fun filter(
        leafPredicate: LeafPredicate = { true },
        treePredicate: TreePredicate = { true }
    ) = ElementFilter(this, leafPredicate, treePredicate).filter()

    override fun render() = TreeElementRenderer(this).render()

    override fun accept(visitor: ElementVisitor) {
        if (!visitor.visit(this)) return

        children.forEach { it.accept(visitor) }

        visitor.endVisit(this)
    }
}
