package com.github.leomartins1999.xmlify.mapper.annotations

import com.github.leomartins1999.xmlify.mapper.xmlify
import com.github.leomartins1999.xmlify.model.LeafElement
import com.github.leomartins1999.xmlify.model.TreeElement
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class XMLIgnoreTest {

    @Test
    fun `object properties marked as ignored are not added as child elements`() {
        data class Person(val name: String, @XMLIgnore val nif: Long)

        val joe = Person("Joe", 123)

        val element = xmlify { joe }
        element as TreeElement

        assertEquals(1, element.children.size)
        assertTrue(element.children.contains(LeafElement("name", "Joe")))
    }
}
