package com.github.leomartins1999.xmlify.mapper.strategies

import com.github.leomartins1999.xmlify.mapper.xmlify
import com.github.leomartins1999.xmlify.model.Element
import com.github.leomartins1999.xmlify.model.element

internal object CollectionMappingStrategy : MappingStrategy {
    override fun toElement(instance: Any): Element {
        instance as Collection<*>

        val children = instance.map { xmlify { it } }

        return element("collection", children)
    }
}
