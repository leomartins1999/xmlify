package com.github.leomartins1999.xmlify.mapper

import com.github.leomartins1999.xmlify.model.LeafElement
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class MapperTests {

    @Test
    fun `maps null as a null element`() {
        val element = xmlify { null }
        assertEquals(LeafElement::class, element::class)

        element as LeafElement
        assertEquals("null", element.name)
        assertNull(element.value)
    }
}
