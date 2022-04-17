package com.github.leomartins1999.xmlify.mapper

import com.github.leomartins1999.xmlify.model.LeafElement
import com.github.leomartins1999.xmlify.model.TreeElement
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class MapperTests {

    @Test
    fun `maps object to element with correct name`() {
        data class Person(val name: String)

        val person = Person("John Doe")

        val element = xmlify { person }

        assertEquals("Person", element.name)
    }

    @Test
    fun `maps object to element with single child`() {
        data class Person(val name: String)

        val person = Person("John Doe")

        val element = xmlify { person }

        assertEquals(1, element.children.size)
        assertEquals(LeafElement("name", "John Doe"), element.children.first())
    }

    @Test
    fun `maps object to element with multiple children`() {
        data class Person(val name: String, val age: Int, val likesSoccer: Boolean)

        val person = Person("John Doe", 25, true)

        val element = xmlify { person }

        assertEquals(3, element.children.size)
        assertTrue(element.children.contains(LeafElement("name", "John Doe")))
        assertTrue(element.children.contains(LeafElement("age", 25)))
        assertTrue(element.children.contains(LeafElement("likesSoccer", true)))
    }

    @Test
    fun `maps object with null property`() {
        data class Person(val name: String? = null)

        val person = Person()

        val element = xmlify { person }

        assertEquals(1, element.children.size)
        assertEquals(LeafElement("name", null), element.children.first())
    }

    @Test
    fun `maps object with nested object`() {
        data class Address(val street: String, val number: Int)
        data class Person(val address: Address)

        val person = Person(Address("Saint John's Street", 13))

        val element = xmlify { person }
        assertEquals(1, element.children.size)

        val nested = element.children.first()
        assertEquals("address", nested.name)
        assertTrue(nested is TreeElement)

        val tree = nested as TreeElement
        assertEquals(2, tree.children.size)
        assertTrue(tree.children.contains(LeafElement("street", "Saint John's Street")))
        assertTrue(tree.children.contains(LeafElement("number", 13)))
    }

    @Test
    fun `maps object with nested null object`() {
        data class Address(val street: String, val number: Int)
        data class Person(val address: Address?)

        val person = Person(null)

        val element = xmlify { person }

        assertEquals(TreeElement("address", listOf()), element.children.first())
    }
}
