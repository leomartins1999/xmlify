package com.github.leomartins1999.xmlify.mapper

import com.github.leomartins1999.xmlify.model.LeafElement
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ValueMapperTests {

    @Test
    fun `maps a String to a LeafElement`() {
        val value = "John Doe"

        val element = xmlify { value }
        assertEquals(LeafElement::class, element::class)

        element as LeafElement
        assertEquals("string", element.name)
        assertEquals(value, element.value)
    }

    @Test
    fun `maps a Float to a LeafElement`() {
        val value = 2.4F

        val element = xmlify { value }
        assertEquals(LeafElement::class, element::class)

        element as LeafElement
        assertEquals("float", element.name)
        assertEquals(value, element.value)
    }

    @Test
    fun `maps an Int to a LeafElement`() {
        val value = 10

        val element = xmlify { value }
        assertEquals(LeafElement::class, element::class)

        element as LeafElement
        assertEquals("int", element.name)
        assertEquals(value, element.value)
    }

    @Test
    fun `maps a Boolean to a LeafElement`() {
        val value = false

        val element = xmlify { value }
        assertEquals(LeafElement::class, element::class)

        element as LeafElement
        assertEquals("boolean", element.name)
        assertEquals(value, element.value)
    }
}
