package com.github.leomartins1999.xmlify.xml

import com.github.leomartins1999.xmlify.element
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
}
