package com.github.leomartins1999.xmlfordummies.xml

data class Element(
    val name: String
) {
    fun render() = elementTemplate(name)
}

fun List<Element>.render() =
    if (isNotEmpty()) joinToString(transform = Element::render, separator = "\n")
    else ""
