package com.github.leomartins1999.xmlify.model

import java.nio.charset.Charset

/**
 * Creates an XML element with a value
 */
fun element(
    name: String,
    value: Any? = null,
    attributes: Map<String, String> = mapOf()
) = LeafElement(name, value, attributes)

/**
 * Creates an XML element with leaf elements
 */
fun element(
    name: String,
    children: List<Element>,
    attributes: Map<String, String> = mapOf()
) = TreeElement(name, children, attributes)

/**
 * Creates an XML document
 */
fun document(
    element: Element,
    version: XMLVersion = XMLVersion.V1_0,
    encoding: Charset = Charsets.UTF_8,
) = Document(version, encoding, element)
