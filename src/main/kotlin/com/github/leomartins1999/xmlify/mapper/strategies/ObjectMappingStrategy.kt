package com.github.leomartins1999.xmlify.mapper.strategies

import com.github.leomartins1999.xmlify.mapper.getName
import com.github.leomartins1999.xmlify.mapper.xmlify
import com.github.leomartins1999.xmlify.model.Element
import com.github.leomartins1999.xmlify.model.element
import kotlin.reflect.KProperty

internal object ObjectMappingStrategy : MappingStrategy {
    override fun toElement(instance: Any) = element(instance::class.getName(), getChildren(instance))

    private fun getChildren(instance: Any): List<Element> {
        val klass = instance::class

        return klass.members
            .filterIsInstance<KProperty<*>>()
            .map { prop -> prop.toElement(instance) }
    }

    private fun KProperty<*>.toElement(instance: Any): Element {
        val name = name
        val value = getter.call(instance)

        return xmlify { value }.copyWithName(name)
    }
}
