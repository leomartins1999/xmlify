package com.github.leomartins1999.xmlify.mapper.strategies

import com.github.leomartins1999.xmlify.mapper.getName
import com.github.leomartins1999.xmlify.model.Element
import com.github.leomartins1999.xmlify.model.element

/**
 * Implementers of this interface are capable
 * of turning an object instance to an Element
 */
interface MappingStrategy {
    fun toElement(instance: Any): Element
}

internal object ValueMappingStrategy : MappingStrategy {
    override fun toElement(instance: Any) = element(instance::class.getName(), instance)
}
