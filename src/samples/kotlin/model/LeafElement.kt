package model

import com.github.leomartins1999.xmlify.model.element

fun leafElementExample() {

    /**
     * Creates a collapsed leaf element (with no value)
     */
    val collapsedElement = element("collapsed")

    /**
     * Creates a collapsed leaf element with an attribute
     */
    val collapsedElementWithAttributes = element("collapsed", attributes = mapOf("attr" to "value"))

    /**
     * Creates a leaf element
     */
    val leafElement = element("leaf", value = 123)

    /**
     * Renders a leaf element
     */
    println(leafElement.render())
}
