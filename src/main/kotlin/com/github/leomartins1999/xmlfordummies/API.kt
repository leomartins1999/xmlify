package com.github.leomartins1999.xmlfordummies

import com.github.leomartins1999.xmlfordummies.xml.Document
import com.github.leomartins1999.xmlfordummies.xml.Element
import java.nio.charset.Charset

enum class XMLVersion(val value: String) {
    V1_0("1.0"), V1_1("1.1"), V2_0("2.0");
}

fun element(name: String) = Element(name)

fun document(
    version: XMLVersion = XMLVersion.V1_0,
    encoding: Charset = Charsets.UTF_8,
    elements: List<Element> = emptyList()
) = Document(version, encoding, elements)
