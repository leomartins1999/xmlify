package com.github.leomartins1999.xmlify.model.visitors

import com.github.leomartins1999.xmlify.model.LeafElement
import com.github.leomartins1999.xmlify.model.TreeElement
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class ElementFilterTests {

    @Test
    fun `filters no elements by default`() {
        val root = TreeElement(
            "root",
            listOf(
                LeafElement("collapsed"),
                LeafElement("boolean", true),
                LeafElement("string", "hello"),
            )
        )

        val result = root.filter()

        assertEquals(root, result)
    }

    @Test
    fun `filters leaf elements`() {
        val root = TreeElement(
            "root",
            listOf(
                LeafElement("collapsed"),
                LeafElement("boolean", true),
                LeafElement("string", "hello"),
            )
        )

        val result = root.filter(leafPredicate = { it.value == true })

        assertEquals(listOf(LeafElement("boolean", true)), result.children)
    }

    @Test
    fun `filters tree elements`() {
        val root = TreeElement(
            "root",
            listOf(
                TreeElement("collapsed"),
                TreeElement("tree1"),
                TreeElement("tree2"),
            )
        )

        val result = root.filter(treePredicate = { it.name == "collapsed" })

        assertEquals(listOf(TreeElement("collapsed")), result.children)
    }

    @Test
    fun `filters nested leaf elements`() {
        val root = TreeElement(
            "root",
            listOf(
                TreeElement(
                    "tree1",
                    listOf(
                        LeafElement("leaf1True", true),
                        LeafElement("leaf1False", false)
                    )
                ),
                TreeElement(
                    "tree2",
                    listOf(
                        LeafElement("leaf2True", true),
                        LeafElement("leaf2False", false)
                    )
                )
            )
        )

        val result = root.filter(leafPredicate = { it.name.contains("2") })

        assertEquals(
            listOf(
                TreeElement("tree1"),
                TreeElement(
                    "tree2",
                    listOf(
                        LeafElement("leaf2True", true),
                        LeafElement("leaf2False", false)
                    )
                )
            ),
            result.children
        )
    }

    @Test
    fun `filters nested tree elements`() {
        val root = TreeElement(
            "root",
            listOf(
                TreeElement(
                    "tree1",
                    listOf(
                        TreeElement("nestedTree1")
                    )
                ),
                TreeElement(
                    "tree2",
                    listOf(
                        TreeElement(
                            "nestedTree2",
                            listOf(
                                TreeElement("leaf2")
                            )
                        )
                    )
                )
            )
        )

        val result = root.filter(treePredicate = { it.name.contains("1") })

        assertEquals(
            listOf(
                TreeElement(
                    "tree1",
                    listOf(
                        TreeElement("nestedTree1")
                    )
                ),
            ),
            result.children
        )
    }

    @Test
    fun `filters elements based on combination of predicates`() {
        val root = TreeElement(
            "root",
            listOf(
                LeafElement("booleanLeaf", true),
                LeafElement("stringLeaf"),
                TreeElement("collapsedTree"),
                TreeElement(
                    "tree",
                    listOf(
                        LeafElement("booleanLeaf", false)
                    )
                )
            )
        )

        val result = root.filter(
            leafPredicate = { it.name == "booleanLeaf" },
            treePredicate = { it.children.isNotEmpty() }
        )

        assertEquals(
            listOf(
                LeafElement("booleanLeaf", true),
                TreeElement("tree", listOf(LeafElement("booleanLeaf", false)))
            ),
            result.children
        )
    }
}
