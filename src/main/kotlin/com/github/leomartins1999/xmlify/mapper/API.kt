package com.github.leomartins1999.xmlify.mapper

/**
 * Maps an object to a Tree Element
 */
fun xmlify(supplier: () -> Any) = toTreeElement(supplier())
