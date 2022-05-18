package com.github.leomartins1999.xmlify.mapper

/**
 * Maps an object to an Element
 */
fun xmlify(supplier: () -> Any?) = toElement(supplier())
