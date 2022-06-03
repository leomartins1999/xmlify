package com.github.leomartins1999.xmlify

import kotlin.reflect.KClass

class UnknownElementTypeException(elementClass: KClass<*>) : RuntimeException("Unknown element type $elementClass!")
