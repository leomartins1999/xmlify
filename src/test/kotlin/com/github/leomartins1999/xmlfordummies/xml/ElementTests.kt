package com.github.leomartins1999.xmlfordummies.xml

import com.github.leomartins1999.xmlfordummies.element
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ElementTests {

    @Test
    fun `element name matches the given name`() {
        val elementName = "myElement"

        val element = element(elementName)

        assertEquals(elementName, element.name)
    }

    @Test
    fun `element is correctly rendered`() {
        val elementName = "myElement"
        val expected = """
            <$elementName>
            </$elementName>
        """.trimIndent()

        val element = element(elementName)

        assertEquals(expected, element.render())
    }
}
