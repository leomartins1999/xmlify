package com.github.leomartins1999.xmlfordummies

fun main() {
    val obj = object {
        val name = "Leo"
        val age = 23
    }

    val xml = xml { obj }

    println(xml)
}
