package com.github.leomartins1999.xmlfordummies

fun main() {
    // simple element
    val element = element("potatoes")
    println("### Simple element ###")
    println(element.render())

    // value element
    val leafElement = element("name", "Jane Doe")
    println("### Leaf element ###")
    println(leafElement.render())

    // xml document containing potatoes
    val document = document(leafElement)
    println("### Document ###")
    println(document)
    println(document.render())
}
