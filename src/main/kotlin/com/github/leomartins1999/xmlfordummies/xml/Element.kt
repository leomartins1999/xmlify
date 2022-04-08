package com.github.leomartins1999.xmlfordummies.xml

data class Element(
    val name: String
) {
    fun render() = elementTemplate(name)
}
