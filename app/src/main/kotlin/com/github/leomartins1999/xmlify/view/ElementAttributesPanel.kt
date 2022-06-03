package com.github.leomartins1999.xmlify.view

import com.github.leomartins1999.xmlify.model.ModelElement
import javax.swing.BorderFactory.createTitledBorder
import javax.swing.JPanel
import javax.swing.JTable

class ElementAttributesPanel(element: ModelElement<*>) : JPanel() {

    private val attributesTable = buildAttributesTable(element.element.attributes)

    init {
        border = panelBorder
        add(attributesTable)
    }

    private fun buildAttributesTable(attributes: Map<String, String>) = JTable(attributes.toKeyValueArray(), columns)

    private fun Map<String, String>.toKeyValueArray() = map { arrayOf(it.key, it.value) }.toTypedArray()

    private companion object {
        private val panelBorder = createTitledBorder("Attributes")
        private val columns = arrayOf("key", "value")
    }
}
