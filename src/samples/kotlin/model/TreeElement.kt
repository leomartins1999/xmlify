package model

import com.github.leomartins1999.xmlify.model.element
import com.github.leomartins1999.xmlify.model.visitors.ElementVisitor

fun treeElementExample() {

    /**
     * Creates a TreeElement without Leaf Elements
     */
    val rootWithoutLeafs = element("root", listOf())

    /**
     * Creates a TreeElement with leaves
     */
    val leaf1 = element("leaf1", true)
    val leaf2 = element("leaf2", false)
    val root = element("root", listOf(leaf1, leaf2))

    /**
     * Renders a tree element
     */
    println(root.render())

    /**
     * You can use the .find() function to fetch elements in a tree
     * that match the predicate
     */
    root.find { it.name == "leaf1" }

    /**
     * You can use the .filter() function to duplicate the root element
     * without unwanted elements
     */
    root.filter(
        leafPredicate = { it.value == true }
    )

    /**
     * Objects who implement the ElementVisitor interface can be supplied to
     * an element to traverse it
     */
    val visitor = object : ElementVisitor {}
    root.accept(visitor)
}
