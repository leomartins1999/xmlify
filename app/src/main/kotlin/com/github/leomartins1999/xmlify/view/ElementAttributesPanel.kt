package com.github.leomartins1999.xmlify.view

import javax.swing.BorderFactory.createTitledBorder
import javax.swing.JPanel
import javax.swing.JTable

class ElementAttributesPanel(private val attributes: Map<String, String>) : JPanel() {

    init {
        render()
    }

    private fun render() {
        border = panelBorder

        add(buildAttributesTable())
    }

    private fun buildAttributesTable() =
        JTable(
            attributes.toKeyValueArray(),
            columns
        )

    private fun Map<String, String>.toKeyValueArray() = map { arrayOf(it.key, it.value) }.toTypedArray()

    private companion object {
        private val panelBorder = createTitledBorder("Attributes")
        private val columns = arrayOf("key", "value")
    }
}
