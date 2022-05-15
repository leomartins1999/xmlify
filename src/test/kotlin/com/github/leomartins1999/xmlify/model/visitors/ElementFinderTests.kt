package com.github.leomartins1999.xmlify.model.visitors

import com.github.leomartins1999.xmlify.model.Element
import com.github.leomartins1999.xmlify.model.LeafElement
import com.github.leomartins1999.xmlify.model.TreeElement
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ElementFinderTests {

    @Test
    fun `finds no elements when tree element is collapsed and does not match filter`() {
        val element = TreeElement("myElement")

        val result = element.find(treePredicate = { it.name == "otherElement" })

        assertEquals(emptyList<Element>(), result)
    }

    @Test
    fun `finds itself if it matches the filter`() {
        val element = TreeElement("myElement")

        val result = element.find(treePredicate = { it.name == "myElement" })

        assertEquals(listOf(element), result)
    }

    @Test
    fun `finds leaf element if it matches the filter`() {
        val leaf = LeafElement("leaf", true)

        val root = TreeElement("root", listOf(leaf))

        val result = root.find(leafPredicate = { it.value == leaf.value })

        assertEquals(listOf(leaf), result)
    }

    @Test
    fun `finds tree element if it matches the filter`() {
        val tree = TreeElement(
            "tree",
            listOf(
                LeafElement("leaf1"),
                LeafElement("leaf2"),
                LeafElement("leaf3")
            )
        )

        val root = TreeElement("root", listOf(tree))

        val result = root.find(treePredicate = { it.children.size == 3 })

        assertEquals(listOf(tree), result)
    }

    @Test
    fun `finds elements nested inside tree elements`() {
        val leaf = LeafElement("leaf")

        val tree = TreeElement("tree", listOf(leaf))

        val root = TreeElement("root", listOf(tree, tree, tree))

        val result = root.find(leafPredicate = { it.name == leaf.name })

        assertEquals(3, result.size)
        assertEquals(listOf(leaf, leaf, leaf), result)
    }
}
