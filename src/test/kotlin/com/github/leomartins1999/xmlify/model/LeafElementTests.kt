package com.github.leomartins1999.xmlify.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class LeafElementTests {

    @Test
    fun `creates a leaf element`() {
        val name = "myElement"
        val value = "potatoes"

        val element = element(name, value)

        assertEquals(name, element.name)
        assertEquals(value, element.value)
    }

    @Test
    fun `creates a collapsed leaf element`() {
        val name = "myElement"
        val value = null

        val element = element(name, value)

        assertEquals(name, element.name)
        assertEquals(value, element.value)
    }

    @Test
    fun `creates a leaf element with a single attribute`() {
        val name = "myElement"
        val value = true
        val attributes = mapOf("language" to "PT")

        val element = element(name, value, attributes)

        assertEquals(name, element.name)
        assertEquals(value, element.value)
        assertEquals(attributes, element.attributes)
    }

    @Test
    fun `creates a leaf element with multiple attributes`() {
        val name = "myElement"
        val value = true
        val attributes = mapOf("language" to "PT", "location" to "Lisbon")

        val element = element(name, value, attributes)

        assertEquals(name, element.name)
        assertEquals(value, element.value)
        assertEquals(attributes, element.attributes)
    }

    @Test
    fun `renders a collapsed leaf element`() {
        val elementName = "myElement"
        val expected = "<$elementName/>"

        val element = element(elementName)

        assertEquals(expected, element.render())
    }

    @Test
    fun `renders a leaf element with integer value`() {
        val name = "age"
        val value = 23

        val expected = "<age>23</age>"

        val element = element(name, value)

        assertEquals(expected, element.render())
    }

    @Test
    fun `renders a leaf element with float value`() {
        val name = "temperature"
        val value = 18.5

        val expected = "<temperature>18.5</temperature>"

        val element = element(name, value)

        assertEquals(expected, element.render())
    }

    @Test
    fun `renders a leaf element with string value`() {
        val name = "name"
        val value = "John Doe"

        val expected = "<name>John Doe</name>"

        val element = element(name, value)

        assertEquals(expected, element.render())
    }

    @Test
    fun `renders a leaf element using the value's toString()`() {
        val name = "cost"
        val value = object {
            val cost = 49.99
            val currency = "€"

            override fun toString() = "$cost $currency"
        }

        val expected = "<cost>49.99 €</cost>"

        val element = element(name, value)

        assertEquals(expected, element.render())
    }

    @Test
    fun `renders a collapsed leaf element with a single attribute`() {
        val name = "myElement"
        val attributes = mapOf("language" to "PT")

        val expected = "<myElement language=\"PT\"/>"

        val element = element(name, attributes = attributes)

        assertEquals(expected, element.render())
    }

    @Test
    fun `renders a leaf element with a single attribute`() {
        val name = "myElement"
        val value = true
        val attributes = mapOf("language" to "PT")

        val expected = "<myElement language=\"PT\">true</myElement>"

        val element = element(name, value, attributes)

        assertEquals(expected, element.render())
    }

    @Test
    fun `renders a leaf element with multiple attributes`() {
        val name = "myElement"
        val value = true
        val attributes = mapOf("language" to "PT", "cost" to "123")

        val expected = "<myElement language=\"PT\" cost=\"123\">true</myElement>"

        val element = element(name, value, attributes)

        assertEquals(expected, element.render())
    }

    @Test
    fun `escapes the attributes of a leaf element`() {
        val name = "myElement"
        val attributes = mapOf("chars" to "\" ' < > &")

        val expected = "<myElement chars=\"&quot; &apos; &lt; &gt; &amp;\"/>"

        val element = element(name, attributes = attributes)

        assertEquals(expected, element.render())
    }

    @Test
    fun `escapes the value of a leaf element`() {
        val name = "myElement"
        val value = "\" ' < > &"

        val expected = "<myElement>&quot; &apos; &lt; &gt; &amp;</myElement>"

        val element = element(name, value)

        assertEquals(expected, element.render())
    }
}
