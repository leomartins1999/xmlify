package com.github.leomartins1999.xmlify.mapper.annotations

import com.github.leomartins1999.xmlify.mapper.xmlify
import com.github.leomartins1999.xmlify.model.TreeElement
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class XMLAttributeTest {

    @Test
    fun `classes marked with XMLAttribute have an XML attribute`() {
        @XMLAttribute("profession", "programmer")
        data class Person(val name: String, val age: Int)

        val person = Person("John", 23)

        val result = xmlify { person }

        assertEquals(mapOf("profession" to "programmer"), result.attributes)
    }

    @Test
    fun `classes can have multiple XMLAttribute references`() {
        @XMLAttribute("profession", "programmer")
        @XMLAttribute("country", "portugal")
        data class Person(val name: String, val age: Int)

        val person = Person("John", 23)

        val result = xmlify { person }

        assertEquals(
            mapOf(
                "profession" to "programmer",
                "country" to "portugal"
            ),
            result.attributes
        )
    }

    @Test
    fun `properties marked with XMLAttribute have an XML attribute`() {
        data class Person(
            @XMLAttribute("fullName", "false")
            val name: String,
            val age: Int
        )

        val person = Person("John", 23)

        val result = xmlify { person }
        assertTrue(result is TreeElement)

        result as TreeElement
        val nameElement = result.children.first { it.name == "name" }

        assertEquals(
            mapOf("fullName" to "false"),
            nameElement.attributes
        )
    }

    @Test
    fun `properties can have multiple XMLAttribute references`() {
        data class Person(
            @XMLAttribute("fullName", "false")
            @XMLAttribute("encoding", "UTF-8")
            val name: String,
            val age: Int
        )

        val person = Person("John", 23)

        val result = xmlify { person }
        assertTrue(result is TreeElement)

        result as TreeElement
        val nameElement = result.children.first { it.name == "name" }

        assertEquals(
            mapOf(
                "fullName" to "false",
                "encoding" to "UTF-8"
            ),
            nameElement.attributes
        )
    }
}
