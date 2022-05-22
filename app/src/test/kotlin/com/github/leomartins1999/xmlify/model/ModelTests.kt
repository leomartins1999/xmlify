package com.github.leomartins1999.xmlify.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.reflect.full.isSubclassOf

class ModelTests {

    @Test
    fun `creates a model with a default element`() {
        val model = Model()

        assertTrue(model.element::class.isSubclassOf(Element::class))
    }

    @Test
    fun `creates a model with the provided element`() {
        val element = element("myTestElement")

        val model = Model(element)

        assertEquals(element, model.element)
    }


}
