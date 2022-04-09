package com.github.leomartins1999.xmlfordummies

import com.github.leomartins1999.xmlfordummies.xml.Document
import com.github.leomartins1999.xmlfordummies.xml.LeafElement
import com.github.leomartins1999.xmlfordummies.xml.XMLVersion
import java.nio.charset.Charset

fun element(
    name: String,
    value: Any? = null
) = LeafElement(name, value)

fun document(
    element: LeafElement,
    version: XMLVersion = XMLVersion.V1_0,
    encoding: Charset = Charsets.UTF_8,
) = Document(version, encoding, element)
