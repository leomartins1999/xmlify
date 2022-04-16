package com.github.leomartins1999.xmlify.model

import java.nio.charset.Charset

enum class XMLVersion(val value: String) {
    V1_0("1.0"), V1_1("1.1"), V2_0("2.0");
}

data class Document internal constructor(
    val version: XMLVersion,
    val encoding: Charset,
    val element: LeafElement
) {
    fun render() = documentTemplate(version.value, encoding.name(), element.render())
}
