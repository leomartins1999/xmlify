package com.github.leomartins1999.xmlify.view

import com.github.leomartins1999.xmlify.model.ElementObserver
import com.github.leomartins1999.xmlify.model.ModelElement
import javax.swing.BorderFactory.createTitledBorder
import javax.swing.JPanel
import javax.swing.JTable
import javax.swing.table.DefaultTableModel

class ElementAttributesPanel(element: ModelElement<*>) : JPanel(), ElementObserver {

    private val table = buildAttributesTable(element.element.attributes)

    init {
        border = panelBorder
        add(table)

        element.subscribe(this)
    }

    override fun onAddAttribute(key: String, value: String) {
        val model = table.model as DefaultTableModel
        model.addRow(arrayOf(key, value))
    }

    override fun onUpdateAttribute(key: String, value: String) =
        table.setValueAt(value, findRowForKey(key), 1)

    override fun onDeleteAttribute(key: String) {
        val rowNumber = findRowForKey(key)

        val model = table.model as DefaultTableModel
        model.removeRow(rowNumber)
    }

    private fun findRowForKey(key: String) = (0..table.rowCount).first {
        table.getValueAt(it, 0) == key
    }

    private fun buildAttributesTable(attributes: Map<String, String>): JTable {
        val model = DefaultTableModel()

        model.addColumn(keyColumn)
        model.addColumn(valueColumn)

        attributes.forEach { (k, v) -> model.addRow(arrayOf(k, v)) }

        return JTable(model)
    }

    private companion object {
        private const val keyColumn = "key"
        private const val valueColumn = "value"
        private val panelBorder = createTitledBorder("Attributes")
    }
}
