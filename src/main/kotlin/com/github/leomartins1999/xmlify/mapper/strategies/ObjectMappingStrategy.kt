package com.github.leomartins1999.xmlify.mapper.strategies

import com.github.leomartins1999.xmlify.mapper.annotations.XMLAttribute
import com.github.leomartins1999.xmlify.mapper.annotations.XMLIgnore
import com.github.leomartins1999.xmlify.mapper.annotations.XMLName
import com.github.leomartins1999.xmlify.mapper.annotations.toAttributes
import com.github.leomartins1999.xmlify.mapper.getName
import com.github.leomartins1999.xmlify.mapper.xmlify
import com.github.leomartins1999.xmlify.model.Element
import com.github.leomartins1999.xmlify.model.element
import kotlin.reflect.KProperty
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.findAnnotations
import kotlin.reflect.full.hasAnnotation

internal object ObjectMappingStrategy : MappingStrategy {
    override fun toElement(instance: Any) = element(instance::class.getName(), getChildren(instance))

    private fun getChildren(instance: Any): List<Element> {
        val klass = instance::class

        return klass.members
            .filterIsInstance<KProperty<*>>()
            .filterNot { it.isIgnored() }
            .map { prop -> prop.toElement(instance) }
    }

    private fun KProperty<*>.isIgnored() = hasAnnotation<XMLIgnore>()

    private fun KProperty<*>.toElement(instance: Any): Element {
        val name = getName()
        val attributes = getAttributes()
        val value = getter.call(instance)

        val element = xmlify { value }

        return element.copyElement(name, attributes)
    }

    private fun KProperty<*>.getName() = findAnnotation<XMLName>()?.name ?: name

    @OptIn(ExperimentalStdlibApi::class)
    private fun KProperty<*>.getAttributes() = findAnnotations<XMLAttribute>().toAttributes()
}
