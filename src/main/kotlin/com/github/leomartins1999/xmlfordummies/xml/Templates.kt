package com.github.leomartins1999.xmlfordummies.xml

fun collapsedElementTemplate(name: String) = "<$name/>"

fun leafElementTemplate(name: String, value: String) = "<$name>$value</$name>"

fun documentTemplate(version: String, encoding: String, elements: String) = """
<?xml version="$version" encoding="$encoding"?>
$elements
""".trimIndent().trim()
