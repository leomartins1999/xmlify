package com.github.leomartins1999.xmlify

import com.github.leomartins1999.xmlify.xml.Document
import com.github.leomartins1999.xmlify.xml.Element
import com.github.leomartins1999.xmlify.xml.LeafElement
import com.github.leomartins1999.xmlify.xml.TreeElement
import com.github.leomartins1999.xmlify.xml.XMLVersion
import java.nio.charset.Charset

fun element(
    name: String,
    value: Any? = null
) = LeafElement(name, value)

fun element(
    name: String,
    children: List<Element>
) = TreeElement(name, children)

fun document(
    element: LeafElement,
    version: XMLVersion = XMLVersion.V1_0,
    encoding: Charset = Charsets.UTF_8,
) = Document(version, encoding, element)
