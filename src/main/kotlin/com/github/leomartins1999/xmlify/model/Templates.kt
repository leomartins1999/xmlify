package com.github.leomartins1999.xmlify.model

private const val space = " "
private const val emptyString = ""

private val charactersToEscapedValues = mapOf(
    "&" to "&amp;",
    "\"" to "&quot;",
    "\'" to "&apos;",
    "<" to "&lt;",
    ">" to "&gt;",
)

internal fun collapsedElementTemplate(element: Element) = "<${element.name}${element.buildAttributesString()}/>"

internal fun leafElementTemplate(element: LeafElement): String {
    val name = element.name
    val attributes = element.buildAttributesString()
    val value = element.value.toString().escapeCharacters()

    return "<$name$attributes>$value</$name>"
}

internal fun treeElementStartTemplate(element: TreeElement) = "<${element.name}${element.buildAttributesString()}>"

internal fun treeElementEndTemplate(element: TreeElement) = "</${element.name}>"

internal fun documentTemplate(version: String, encoding: String, elements: String) = """
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
