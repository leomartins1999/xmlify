package com.github.leomartins1999.xmlify.model

import com.github.leomartins1999.xmlify.element
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TreeElementTests {

    @Test
    fun `creates a tree element without elements`() {
        val name = "root"
        val children = emptyList<Element>()

        val element = element(name, children)

        assertEquals(name, element.name)
        assertEquals(children, element.children)
    }

    @Test
    fun `creates a tree element with a single element`() {
        val name = "root"
        val children = listOf<Element>(LeafElement("leaf"))

        val element = element(name, children)

        assertEquals(name, element.name)
        assertEquals(children, element.children)
    }

    @Test
    fun `creates a tree element with multiple elements`() {
        val name = "root"
        val children = listOf(LeafElement("leaf"), TreeElement("tree"))

        val element = element(name, children)

        assertEquals(name, element.name)
        assertEquals(children, element.children)
    }

    @Test
    fun `creates a tree element with attributes`() {
        val name = "root"
        val children = listOf<Element>()
        val attributes = mapOf("language" to "PT")

        val element = element(name, children, attributes)

        assertEquals(name, element.name)
        assertEquals(children, element.children)
        assertEquals(attributes, element.attributes)
    }
}
