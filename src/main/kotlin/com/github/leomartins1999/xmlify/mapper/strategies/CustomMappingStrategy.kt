package com.github.leomartins1999.xmlify.mapper.strategies

import com.github.leomartins1999.xmlify.mapper.annotations.XMLMapper
import com.github.leomartins1999.xmlify.mapper.annotations.instantiateMapper
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation

internal object CustomMappingStrategy : MappingStrategy {
    override fun toElement(instance: Any) = instance::class
        .getCustomMapper()
        .instantiateMapper()
        .toElement(instance)

    private fun KClass<*>.getCustomMapper() = findAnnotation<XMLMapper>()!!.strategyClass
}
