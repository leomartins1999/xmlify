package com.github.leomartins1999.xmlify.mapper

import com.github.leomartins1999.xmlify.model.LeafElement
import com.github.leomartins1999.xmlify.model.TreeElement
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class ObjectMapperTests {

    @Test
    fun `maps object with one property`() {
        data class Person(val name: String)

        val person = Person("John Doe")

        val element = xmlify { person }
        assertEquals(TreeElement::class, element::class)

        element as TreeElement
        assertEquals("Person", element.name)
        assertEquals(1, element.children.size)
        assertEquals(LeafElement("name", "John Doe"), element.children.first())
    }

    @Test
    fun `maps object with multiple properties`() {
        data class Person(val name: String, val age: Int, val likesSoccer: Boolean)

        val person = Person("John Doe", 25, true)

        val element = xmlify { person }
        assertEquals(TreeElement::class, element::class)

        element as TreeElement
        assertEquals("Person", element.name)
        assertEquals(3, element.children.size)
        assertTrue(
            element.children.containsAll(
                listOf(
                    LeafElement("name", "John Doe"),
                    LeafElement("age", 25),
                    LeafElement("likesSoccer", true)
                )
            )
        )
    }

    @Test
    fun `maps object with null property`() {
        data class Person(val name: String? = null)

        val person = Person()

        val element = xmlify { person }
        assertEquals(TreeElement::class, element::class)

        element as TreeElement
        assertEquals("Person", element.name)
        assertEquals(1, element.children.size)
        assertEquals(LeafElement("name", null), element.children.first())
    }

    @Test
    fun `maps object with nested object property`() {
        data class Address(val street: String, val number: Int)
        data class Person(val address: Address)

        val person = Person(Address("Saint John's Street", 13))

        val element = xmlify { person }
        assertEquals(TreeElement::class, element::class)

        element as TreeElement
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
    fun `maps an object with a nested collection`() {
        data class Workweek(val days: List<String>)

        val workweek = Workweek(listOf("Sunday", "Tuesday", "Wednesday"))

        val element = xmlify { workweek }
        assertEquals(TreeElement::class, element::class)

        element as TreeElement
        assertEquals("Workweek", element.name)
        assertTrue(
            element.children.contains(
                TreeElement(
                    "days",
                    listOf(
                        LeafElement("String", "Sunday"),
                        LeafElement("String", "Tuesday"),
                        LeafElement("String", "Wednesday")
                    )
                )
            )
        )
    }
}
