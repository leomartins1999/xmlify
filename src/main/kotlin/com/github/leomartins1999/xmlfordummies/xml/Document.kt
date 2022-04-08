package com.github.leomartins1999.xmlfordummies.xml

import com.github.leomartins1999.xmlfordummies.XMLVersion
import java.nio.charset.Charset

data class Document(
    val version: XMLVersion,
    val encoding: Charset,
    val elements: List<Element>
) {
    fun render() = documentTemplate(version.value, encoding.name(), elements.render())
}
