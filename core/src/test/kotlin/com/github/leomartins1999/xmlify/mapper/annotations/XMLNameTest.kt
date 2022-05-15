package com.github.leomartins1999.xmlify.mapper.annotations

import com.github.leomartins1999.xmlify.mapper.xmlify
import com.github.leomartins1999.xmlify.model.TreeElement
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class XMLNameTest {

    @Test
    fun `annotation sets the element name`() {
        @XMLName("myResource")
        data class Resource(val id: Long)

        val resource = Resource(1)

        val element = xmlify { resource }

        assertEquals("myResource", element.name)
    }

    @Test
    fun `annotation sets the child element name`() {
        data class Resource(@XMLName("myId") val id: Long)

        val resource = Resource(1)

        val element = xmlify { resource }
        element as TreeElement

        assertEquals("myId", element.children.first().name)
    }
}
