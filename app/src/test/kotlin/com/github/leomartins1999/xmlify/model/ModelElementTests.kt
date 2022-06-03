package com.github.leomartins1999.xmlify.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ModelElementTests {

    @Test
    fun `builds a ModelLeafElement from a Leaf Element`() {
        val element = element("leaf1", true)

        val model = ModelLeafElement(element, 3, -1)

        assertEquals(element, model.element)
        assertEquals(3, model.elementId)
        assertEquals(-1, model.previousElementId)
    }

    @Test
    fun `builds a ModelTreeElement from a Tree Element`() {
        val element = element("leaf1", children = listOf())

        val model = ModelTreeElement(element, 2, 1)

        assertEquals(element, model.element)
        assertEquals(2, model.elementId)
        assertEquals(1, model.previousElementId)
    }

    @Test
    fun `updates the name of a Leaf Element and notifies observers`() {
        val observer = object : ElementObserver {
            var notified = false
            override fun onChangeName(newName: String) {
                notified = true
            }
        }

        val element = element("oldName")
        val model = ModelLeafElement(element, 1, -1)
        model.subscribe(observer)

        model.updateName("newName")

        assertEquals("newName", model.element.name)
        assertTrue(observer.notified)
    }

    @Test
    fun `updates the name of a Tree Element and notifies observers`() {
        val observer = object : ElementObserver {
            var notified = false
            override fun onChangeName(newName: String) {
                notified = true
            }
        }

        val element = element("oldRoot", children = listOf())
        val model = ModelTreeElement(element, 1, -1)
        model.subscribe(observer)

        model.updateName("newRoot")

        assertEquals("newRoot", model.element.name)
        assertTrue(observer.notified)
    }
}
