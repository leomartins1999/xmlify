package com.github.leomartins1999.xmlfordummies.xml

fun elementTemplate(name: String) = """
<$name>
</$name>
""".trimIndent().trim()

fun valueElementTemplate(name: String, value: String) = """
<$name>$value</$name>
""".trimIndent()

fun documentTemplate(version: String, encoding: String, elements: String) = """
<?xml version="$version" encoding="$encoding"?>
$elements
""".trimIndent().trim()
