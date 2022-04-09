package com.github.leomartins1999.xmlify.xml

const val space = " "
const val emptyString = ""

fun collapsedElementTemplate(element: Element) =
    "<${element.name}${element.buildAttributesString()}/>"

fun leafElementTemplate(element: LeafElement) =
    "<${element.name}${element.buildAttributesString()}>${element.value}</${element.name}>"

fun treeElementStartTemplate(element: TreeElement) = "<${element.name}${element.buildAttributesString()}>"

fun treeElementEndTemplate(element: TreeElement) = "</${element.name}>"

fun documentTemplate(version: String, encoding: String, elements: String) = """
<?xml version="$version" encoding="$encoding"?>
$elements
""".trimIndent().trim()

private fun Element.buildAttributesString() =
    if (attributes.isEmpty()) emptyString
    else attributes.toList().joinToString(
        prefix = space,
        separator = space,
        transform = { "${it.first}=\"${it.second}\"" }
    )
