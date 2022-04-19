package com.github.leomartins1999.xmlify.mapper

import com.github.leomartins1999.xmlify.model.LeafElement
import com.github.leomartins1999.xmlify.model.TreeElement
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CollectionMapperTests {

    @Test
    fun `maps a collection of values`() {
        val numbers = listOf(1, 2, 3, 4, 5)

        val element = xmlify { numbers }
        assertEquals(TreeElement::class, element::class)

        element as TreeElement
        assertEquals("collection", element.name)
        assertEquals(5, element.children.size)
        assertTrue(
            element.children.containsAll(
                listOf(
                    LeafElement("int", 1),
                    LeafElement("int", 2),
                    LeafElement("int", 3),
                    LeafElement("int", 4),
                    LeafElement("int", 5)
                )
            )
        )
    }

    @Test
    fun `maps a collection of objects`() {
        data class Person(val name: String)

        val persons = listOf(Person("John"), Person("Jane"), Person("Joe"))

        val element = xmlify { persons }
        assertEquals(TreeElement::class, element::class)

        element as TreeElement
        assertEquals("collection", element.name)
        assertEquals(3, element.children.size)
        assertTrue(
            element.children.containsAll(
                listOf(
                    TreeElement("person", listOf(LeafElement("name", "John"))),
                    TreeElement("person", listOf(LeafElement("name", "Jane"))),
                    TreeElement("person", listOf(LeafElement("name", "Joe")))
                )
            )
        )
    }
}
