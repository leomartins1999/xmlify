package com.github.leomartins1999.xmlify.mapper

import com.github.leomartins1999.xmlify.model.Element
import com.github.leomartins1999.xmlify.model.TreeElement
import com.github.leomartins1999.xmlify.model.element
import kotlin.reflect.KClass
import kotlin.reflect.KProperty
import kotlin.reflect.full.isSubclassOf

internal fun toTreeElement(instance: Any): TreeElement {
    val klass = instance::class

    val name = getElementName(klass)
    val children = getChildElements(klass, instance)

    return element(name, children)
}

private fun getElementName(klass: KClass<*>) = klass.simpleName!!

private fun getChildElements(klass: KClass<*>, instance: Any) = klass.members
    .filterIsInstance<KProperty<Any>>()
    .map { toElement(it, instance) }

private fun toElement(prop: KProperty<Any>, instance: Any): Element {
    val name = prop.name
    val value = prop.getter.call(instance)
    val type = prop.returnType.classifier as KClass<*>

    return when {
        isValueType(type) -> element(name, value)
        value == null -> element(name, listOf())
        else -> toTreeElement(value).copy(elementName = name)
    }
}

private fun isValueType(type: KClass<*>) =
    type.isSubclassOf(Number::class) || type == String::class || type == Boolean::class
