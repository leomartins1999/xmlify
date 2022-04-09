package com.github.leomartins1999.xmlify.xml

abstract class Element(val name: String) {
    abstract fun render(): String
    abstract fun accept(visitor: ElementVisitor)
}

class LeafElement(name: String, val value: Any? = null) : Element(name) {
    override fun render() = value
        ?.let { leafElementTemplate(name, it.toString()) }
        ?: collapsedElementTemplate(name)

    override fun accept(visitor: ElementVisitor): Unit = visitor.visit(this)
}

class TreeElement(name: String, val children: List<Element> = emptyList()) : Element(name) {
    fun hasChildren() = children.isNotEmpty()

    override fun render() = TreeElementRenderer().render(this)

    override fun accept(visitor: ElementVisitor) {
        if (!visitor.visit(this)) return

        children.forEach { it.accept(visitor) }

        visitor.endVisit(this)
    }
}
