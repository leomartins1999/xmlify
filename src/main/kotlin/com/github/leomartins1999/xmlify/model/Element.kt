package com.github.leomartins1999.xmlify.model

import com.github.leomartins1999.xmlify.model.visitors.ElementFinder
import com.github.leomartins1999.xmlify.model.visitors.ElementVisitor
import com.github.leomartins1999.xmlify.model.visitors.TreeElementRenderer

abstract class Element(val name: String, val attributes: Map<String, String>) {
    abstract fun render(): String
    abstract fun accept(visitor: ElementVisitor)
}

class LeafElement(
    name: String,
    val value: Any? = null,
    attributes: Map<String, String> = mapOf()
) : Element(name, attributes) {
    override fun render() = value
        ?.let { leafElementTemplate(this) }
        ?: collapsedElementTemplate(this)

    override fun accept(visitor: ElementVisitor): Unit = visitor.visit(this)
}

class TreeElement(
    name: String,
    val children: List<Element> = emptyList(),
    attributes: Map<String, String> = mapOf()
) : Element(name, attributes) {
    fun hasChildren() = children.isNotEmpty()

    fun find(filter: (Element) -> Boolean) = ElementFinder(this, filter).find()

    override fun render() = TreeElementRenderer(this).render()

    override fun accept(visitor: ElementVisitor) {
        if (!visitor.visit(this)) return

        children.forEach { it.accept(visitor) }

        visitor.endVisit(this)
    }
}
