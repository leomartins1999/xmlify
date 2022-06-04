package com.github.leomartins1999.xmlify.model

import com.github.leomartins1999.xmlify.exceptions.ElementNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.reflect.full.isSubclassOf

class ModelTests {

    @Test
    fun `creates a model with a default element`() {
        val model = Model()

        assertTrue(model.root.element::class.isSubclassOf(Element::class))
    }

    @Test
    fun `creates a model with the provided element`() {
        val element = element("myTestElement")

        val model = Model(element)

        assertEquals(element, model.root.element)
    }

    @Test
    fun `updates the name of the intended element`() {
        val element = element("myTestElement")

        val model = Model(element)

        model.updateName(model.root.elementId, "myOtherTestElement")

        assertEquals(element("myOtherTestElement"), model.root.element)
    }

    @Test
    fun `throws NoSuchElementException when trying to update the name of an unexisting element`() {
        val model = Model()

        assertThrows<ElementNotFoundException> { model.updateName(1000, "thisWillRaiseAnError") }
    }

    @Test
    fun `returns the children of a given element`() {
        val leaf1 = element("leaf1")
        val leaf2 = element("leaf2")
        val leaf3 = element("leaf3")

        val root = element(name = "root", children = listOf(leaf1, leaf2, leaf3))

        val model = Model(root)

        val result = model.getChildren(model.root.elementId)

        assertTrue(result.any { it.element == leaf1 })
        assertTrue(result.any { it.element == leaf2 })
        assertTrue(result.any { it.element == leaf3 })
    }
}
