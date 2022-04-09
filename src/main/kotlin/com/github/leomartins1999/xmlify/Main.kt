package com.github.leomartins1999.xmlify

fun main() {
    // simple element
    val element = element("potatoes")
    println("### Simple element ###")
    println(element.render())

    // leaf element
    val leafElement = element("name", "Jane Doe")
    println("### Leaf element ###")
    println(leafElement.render())

    // tree element
    val treeElement = element(
        "person",
        listOf(
            leafElement,
            element
        )
    )
    println("### Tree element ###")
    println(treeElement.render())

    // xml document
    val document = document(leafElement)
    println("### Document ###")
    println(document)
    println(document.render())
}
