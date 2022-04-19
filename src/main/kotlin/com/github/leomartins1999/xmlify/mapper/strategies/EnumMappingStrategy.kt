package com.github.leomartins1999.xmlify.mapper.strategies

import com.github.leomartins1999.xmlify.mapper.getName
import com.github.leomartins1999.xmlify.model.Element
import com.github.leomartins1999.xmlify.model.element

object EnumMappingStrategy : MappingStrategy {
    override fun toElement(instance: Any): Element {
        instance as Enum<*>

        return element(instance::class.getName(), instance.name)
    }
}
