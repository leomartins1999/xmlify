package com.github.leomartins1999.xmlify.model

import java.nio.charset.Charset

fun element(
    name: String,
    value: Any? = null,
    attributes: Map<String, String> = mapOf()
) = LeafElement(name, value, attributes)

fun element(
    name: String,
    children: List<Element>,
    attributes: Map<String, String> = mapOf()
) = TreeElement(name, children, attributes)

fun document(
    element: LeafElement,
    version: XMLVersion = XMLVersion.V1_0,
    encoding: Charset = Charsets.UTF_8,
) = Document(version, encoding, element)
