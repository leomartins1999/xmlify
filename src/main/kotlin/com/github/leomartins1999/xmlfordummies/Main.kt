package com.github.leomartins1999.xmlfordummies

fun main() {
    // simple element
    val element = element("potatoes")
    println(element)
    println(element.render())

    // xml document containing potatoes
    val document = document(elements = listOf(element))
    println(document)
    println(document.render())
}
