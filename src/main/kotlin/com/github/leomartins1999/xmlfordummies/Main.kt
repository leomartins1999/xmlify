package com.github.leomartins1999.xmlfordummies

fun main() {
    // simple element
    val element = element("potatoes")
    println("### Simple element ###")
    println(element.render())

    // value element
    val valueElement = element("name", "Jane Doe")
    println("### Value element ###")
    println(valueElement.render())

    // xml document containing potatoes
    val document = document(valueElement)
    println("### Document ###")
    println(document)
    println(document.render())
}
