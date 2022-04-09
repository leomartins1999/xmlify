package com.github.leomartins1999.xmlify.xml

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

    override fun render() = TreeElementRenderer().render(this)

    override fun accept(visitor: ElementVisitor) {
        if (!visitor.visit(this)) return

        children.forEach { it.accept(visitor) }

        visitor.endVisit(this)
    }
}
