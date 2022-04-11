package com.github.leomartins1999.xmlify.model

import com.github.leomartins1999.xmlify.element
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class TreeElementTests {

    @Test
    fun `creates a tree element without elements`() {
        val name = "root"
        val children = emptyList<Element>()

        val element = element(name, children)

        assertEquals(name, element.name)
        assertEquals(children, element.children)
    }

    @Test
    fun `creates a tree element with a single element`() {
        val name = "root"
        val children = listOf<Element>(LeafElement("leaf"))

        val element = element(name, children)

        assertEquals(name, element.name)
        assertEquals(children, element.children)
    }

    @Test
    fun `creates a tree element with multiple elements`() {
        val name = "root"
        val children = listOf(LeafElement("leaf"), TreeElement("tree"))

        val element = element(name, children)

        assertEquals(name, element.name)
        assertEquals(children, element.children)
    }

    @Test
    fun `creates a tree element with attributes`() {
        val name = "root"
        val children = listOf<Element>()
        val attributes = mapOf("language" to "PT")

        val element = element(name, children, attributes)

        assertEquals(name, element.name)
        assertEquals(children, element.children)
        assertEquals(attributes, element.attributes)
    }

    @Test
    fun `renders an empty tree element`() {
        val name = "root"
        val children = emptyList<Element>()

        val expected = "<root/>"

        val element = element(name, children)

        assertEquals(expected, element.render())
    }

    @Test
    fun `renders a tree element with a collapsed leaf element`() {
        val name = "root"
        val children = listOf(LeafElement("leaf"))

        val expected = """
            <root>
            ${"\t"}<leaf/>
            </root>
        """.trimIndent()

        val element = element(name, children)

        assertEquals(expected, element.render())
    }

    @Test
    fun `renders a tree element with a leaf element`() {
        val name = "root"
        val children = listOf(LeafElement("leaf", true))

        val expected = """
            <root>
            ${"\t"}<leaf>true</leaf>
            </root>
        """.trimIndent()

        val element = element(name, children)

        assertEquals(expected, element.render())
    }

    @Test
    fun `renders a tree element with multiple leaf elements`() {
        val name = "root"
        val children = listOf(
            LeafElement("leaf", true),
            LeafElement("collapsed"),
            LeafElement("anotherLeaf", 10)
        )

        val expected = """
            <root>
            ${"\t"}<leaf>true</leaf>
            ${"\t"}<collapsed/>
            ${"\t"}<anotherLeaf>10</anotherLeaf>
            </root>
        """.trimIndent()

        val element = element(name, children)

        assertEquals(expected, element.render())
    }

    @Test
    fun `renders a tree element with a collapsed tree element`() {
        val name = "root"
        val children = listOf(TreeElement("tree"))

        val expected = """
            <root>
            ${"\t"}<tree/>
            </root>
        """.trimIndent()

        val element = element(name, children)

        assertEquals(expected, element.render())
    }

    @Test
    fun `renders a tree element with a tree element`() {
        val name = "root"
        val children = listOf(
            TreeElement(
                "tree",
                listOf(
                    LeafElement("collapsed"),
                    LeafElement("leaf", true)
                )
            )
        )

        val expected = """
            <root>
            ${"\t"}<tree>
            ${"\t\t"}<collapsed/>
            ${"\t\t"}<leaf>true</leaf>
            ${"\t"}</tree>
            </root>
        """.trimIndent()

        val element = element(name, children)

        assertEquals(expected, element.render())
    }

    @Test
    fun `renders a tree element with a combination of leaf and tree elements`() {
        val name = "person"
        val children = listOf(
            LeafElement("name", "Jane Doe"),
            LeafElement("age", 23),
            LeafElement("female"),
            TreeElement(
                "address",
                listOf(
                    LeafElement("street", "Saint James' Street"),
                    LeafElement("apartment"),
                    TreeElement(
                        "postalCode",
                        listOf(
                            LeafElement("code", "2000-125"),
                            LeafElement("city", "London")
                        )
                    )
                )
            )
        )

        val expected = """
            <person>
            ${"\t"}<name>Jane Doe</name>
            ${"\t"}<age>23</age>
            ${"\t"}<female/>
            ${"\t"}<address>
            ${"\t\t"}<street>Saint James&apos; Street</street>
            ${"\t\t"}<apartment/>
            ${"\t\t"}<postalCode>
            ${"\t\t\t"}<code>2000-125</code>
            ${"\t\t\t"}<city>London</city>
            ${"\t\t"}</postalCode>
            ${"\t"}</address>
            </person>
        """.trimIndent()

        val element = element(name, children)

        assertEquals(expected, element.render())
    }

    @Test
    fun `renders a collapsed tree element with one attribute`() {
        val name = "root"
        val children = listOf<Element>()
        val attributes = mapOf("language" to "PT")

        val expected = "<root language=\"PT\"/>"

        val element = element(name, children, attributes)

        assertEquals(expected, element.render())
    }

    @Test
    fun `renders a tree element with one attribute`() {
        val name = "root"
        val children = listOf<Element>(LeafElement("leaf"))
        val attributes = mapOf("language" to "PT")

        val expected = """
            <root language="PT">
            ${"\t"}<leaf/>
            </root>
        """.trimIndent()

        val element = element(name, children, attributes)

        assertEquals(expected, element.render())
    }

    @Test
    fun `renders a tree element with multiple attributes`() {
        val name = "root"
        val children = listOf<Element>(LeafElement("leaf"))
        val attributes = mapOf("language" to "PT", "city" to "Lisbon")

        val expected = """
            <root language="PT" city="Lisbon">
            ${"\t"}<leaf/>
            </root>
        """.trimIndent()

        val element = element(name, children, attributes)

        assertEquals(expected, element.render())
    }

    @Test
    fun `escapes the attributes of a tree element`() {
        val name = "myElement"
        val attributes = mapOf("chars" to "\" ' < > &")
        val children = listOf<Element>()

        val expected = "<myElement chars=\"&quot; &apos; &lt; &gt; &amp;\"/>"

        val element = element(name, children, attributes = attributes)

        assertEquals(expected, element.render())
    }
}
