package com.github.leomartins1999.xmlfordummies.xml

data class Element(
    val name: String,
    val value: Any? = null
) {
    fun render() = value
        ?.let { valueElementTemplate(name, it.toString()) }
        ?: elementTemplate(name)
}

fun List<Element>.render() = joinToString(transform = Element::render, separator = "\n")
