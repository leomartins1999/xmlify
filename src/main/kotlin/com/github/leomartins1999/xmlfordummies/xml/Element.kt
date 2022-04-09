package com.github.leomartins1999.xmlfordummies.xml

abstract class Element(val name: String) {
    abstract fun render(): String
    abstract fun accept(elementVisitor: ElementVisitor)
}

class LeafElement(name: String, val value: Any? = null) : Element(name) {
    override fun render() = value
        ?.let { leafElementTemplate(name, it.toString()) }
        ?: collapsedElementTemplate(name)

    override fun accept(elementVisitor: ElementVisitor): Unit = elementVisitor.visit(this)
}
