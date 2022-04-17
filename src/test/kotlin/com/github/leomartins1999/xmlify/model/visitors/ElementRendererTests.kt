package com.github.leomartins1999.xmlify.model.visitors

import com.github.leomartins1999.xmlify.model.Element
import com.github.leomartins1999.xmlify.model.LeafElement
import com.github.leomartins1999.xmlify.model.TreeElement
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ElementRendererTests {

    @Test
    fun `renders an empty tree element`() {
        val name = "root"
        val children = emptyList<Element>()
        val element = TreeElement(name, children)

        val expected = "<root/>"

        val result = ElementRenderer(element).render()

        assertEquals(expected, result)
    }

    @Test
    fun `renders a tree element with a collapsed leaf element`() {
        val name = "root"
        val children = listOf(LeafElement("leaf"))
        val element = TreeElement(name, children)

        val expected = """
            <root>
            ${"\t"}<leaf/>
            </root>
        """.trimIndent()

        val result = ElementRenderer(element).render()

        assertEquals(expected, result)
    }

    @Test
    fun `renders a tree element with a leaf element`() {
        val name = "root"
        val children = listOf(LeafElement("leaf", true))
        val element = TreeElement(name, children)

        val expected = """
            <root>
            ${"\t"}<leaf>true</leaf>
            </root>
        """.trimIndent()

        val result = ElementRenderer(element).render()

        assertEquals(expected, result)
    }

    @Test
    fun `renders a tree element with multiple leaf elements`() {
        val name = "root"
        val children = listOf(
            LeafElement("leaf", true),
            LeafElement("collapsed"),
            LeafElement("anotherLeaf", 10)
        )
        val element = TreeElement(name, children)

        val expected = """
            <root>
            ${"\t"}<leaf>true</leaf>
            ${"\t"}<collapsed/>
            ${"\t"}<anotherLeaf>10</anotherLeaf>
            </root>
        """.trimIndent()

        val result = ElementRenderer(element).render()

        assertEquals(expected, result)
    }

    @Test
    fun `renders a tree element with a collapsed tree element`() {
        val name = "root"
        val children = listOf(TreeElement("tree"))
        val element = TreeElement(name, children)

        val expected = """
            <root>
            ${"\t"}<tree/>
            </root>
        """.trimIndent()

        val result = ElementRenderer(element).render()

        assertEquals(expected, result)
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
        val element = TreeElement(name, children)

        val expected = """
            <root>
            ${"\t"}<tree>
            ${"\t\t"}<collapsed/>
            ${"\t\t"}<leaf>true</leaf>
            ${"\t"}</tree>
            </root>
        """.trimIndent()

        val result = ElementRenderer(element).render()

        assertEquals(expected, result)
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
        val element = TreeElement(name, children)

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

        val result = ElementRenderer(element).render()

        assertEquals(expected, result)
    }

    @Test
    fun `renders a collapsed tree element with one attribute`() {
        val name = "root"
        val children = listOf<Element>()
        val attributes = mapOf("language" to "PT")
        val element = TreeElement(name, children, attributes)

        val expected = "<root language=\"PT\"/>"

        val result = ElementRenderer(element).render()

        assertEquals(expected, result)
    }

    @Test
    fun `renders a tree element with one attribute`() {
        val name = "root"
        val children = listOf<Element>(LeafElement("leaf"))
        val attributes = mapOf("language" to "PT")
        val element = TreeElement(name, children, attributes)

        val expected = """
            <root language="PT">
            ${"\t"}<leaf/>
            </root>
        """.trimIndent()

        val result = ElementRenderer(element).render()

        assertEquals(expected, result)
    }

    @Test
    fun `renders a tree element with multiple attributes`() {
        val name = "root"
        val children = listOf<Element>(LeafElement("leaf"))
        val attributes = mapOf("language" to "PT", "city" to "Lisbon")
        val element = TreeElement(name, children, attributes)

        val expected = """
            <root language="PT" city="Lisbon">
            ${"\t"}<leaf/>
            </root>
        """.trimIndent()

        val result = ElementRenderer(element).render()

        assertEquals(expected, result)
    }

    @Test
    fun `escapes the attributes of a tree element`() {
        val name = "myElement"
        val attributes = mapOf("chars" to "\" ' < > &")
        val children = listOf<Element>()
        val element = TreeElement(name, children, attributes)

        val expected = "<myElement chars=\"&quot; &apos; &lt; &gt; &amp;\"/>"

        val result = ElementRenderer(element).render()

        assertEquals(expected, result)
    }
}
