package com.github.leomartins1999.xmlfordummies

import com.github.leomartins1999.xmlfordummies.xml.Document
import com.github.leomartins1999.xmlfordummies.xml.Element
import com.github.leomartins1999.xmlfordummies.xml.XMLVersion
import java.nio.charset.Charset

fun element(name: String) = Element(name)

fun document(
    version: XMLVersion = XMLVersion.V1_0,
    encoding: Charset = Charsets.UTF_8,
    elements: List<Element> = emptyList()
) = Document(version, encoding, elements)
