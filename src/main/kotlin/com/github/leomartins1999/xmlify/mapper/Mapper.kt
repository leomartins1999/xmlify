package com.github.leomartins1999.xmlify.mapper

import com.github.leomartins1999.xmlify.model.Element
import com.github.leomartins1999.xmlify.model.TreeElement
import com.github.leomartins1999.xmlify.model.element
import kotlin.reflect.KClass
import kotlin.reflect.KProperty

internal fun toTreeElement(instance: Any): TreeElement {
    val klass = instance::class

    val name = getElementName(klass)
    val children = getChildElements(klass, instance)

    return element(name, children)
}

private fun getElementName(klass: KClass<out Any>) = klass.simpleName!!

private fun getChildElements(klass: KClass<out Any>, instance: Any) = klass.members
    .filterIsInstance<KProperty<Any>>()
    .map { toElement(it, instance) }

private fun toElement(prop: KProperty<Any>, instance: Any): Element {
    val name = prop.name
    val value = prop.getter.call(instance)

    return element(name, value)
}
