package com.github.leomartins1999.xmlify.xml

const val space = " "
const val emptyString = ""

val charactersToEscapedValues = mapOf(
    "&" to "&amp;",
    "\"" to "&quot;",
    "\'" to "&apos;",
    "<" to "&lt;",
    ">" to "&gt;",
)

fun collapsedElementTemplate(element: Element) = "<${element.name}${element.buildAttributesString()}/>"

fun leafElementTemplate(element: LeafElement): String {
    val name = element.name
    val attributes = element.buildAttributesString()
    val value = element.value.toString().escapeCharacters()

    return "<$name$attributes>$value</$name>"
}

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
        transform = { "${it.first}=\"${it.second.escapeCharacters()}\"" }
    )

private fun String.escapeCharacters() =
    charactersToEscapedValues
        .toList()
        .fold(this) { str, char ->
            str.replace(char.first, char.second)
        }
