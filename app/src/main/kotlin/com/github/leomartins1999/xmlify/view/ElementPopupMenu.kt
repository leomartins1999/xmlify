package com.github.leomartins1999.xmlify.view

import com.github.leomartins1999.xmlify.Controller
import com.github.leomartins1999.xmlify.model.ElementObserver
import com.github.leomartins1999.xmlify.model.ModelElement
import com.github.leomartins1999.xmlify.model.ModelLeafElement
import javax.swing.JMenuItem
import javax.swing.JOptionPane
import javax.swing.JPopupMenu


class ElementPopupMenu(
    private val controller: Controller,
    private val element: ModelElement<*>
) : JPopupMenu(), ElementObserver {

    private val menuHeader = menuHeader(getHeaderText(element.element.name))

    init {
        add(menuHeader)

        add(buildAttributeActionsHeader())
        add(buildAddAttributeAction())
        add(buildUpdateAttributeAction())
        add(buildDeleteAttributeAction())

        add(buildElementActionsHeader())
        add(buildUpdateNameAction())

        if (element is ModelLeafElement) {
            add(buildUpdateValueAction())
        } else {
            add(buildAddElementAction())
        }

        add(buildDeleteElementAction())

        element.subscribe(this)
    }

    override fun onUpdateName(newName: String) {
        menuHeader.text = getHeaderText(newName)
    }

    private fun buildAttributeActionsHeader() = menuHeader("Attribute actions")

    private fun buildAddAttributeAction() = menuItem("Add attribute") {
        val key = promptInput("Attribute key")
        val value = promptInput("Attribute value")
        controller.addAttribute(element.elementId, key, value)
    }

    private fun buildUpdateAttributeAction() = menuItem("Update attribute") {
        val key = promptInput("Attribute key")
        val value = promptInput("Attribute value")
        controller.updateAttribute(element.elementId, key, value)
    }

    private fun buildDeleteAttributeAction() = menuItem("Delete attribute") {
        val key = promptInput("Attribute key")
        controller.deleteAttribute(element.elementId, key)
    }

    private fun buildElementActionsHeader() = menuHeader("Element actions")

    private fun buildUpdateNameAction() = menuItem("Update name") {
        val newName = promptInput("New name")
        controller.updateName(element.elementId, newName)
    }

    private fun buildUpdateValueAction() = menuItem("Update value") {
        val newValue = promptInput("New value")
        controller.updateValue(element.elementId, newValue)
    }

    private fun buildAddElementAction() = menuItem("Add element") {
        val elementType = promptInput("Element type (must be 'leaf' or 'tree')")
        val elementName = promptInput("Element name")
        controller.addElement(element.elementId, elementType, elementName)
    }

    private fun buildDeleteElementAction() = menuItem("Delete element") {
        controller.deleteElement(element.elementId)
    }

    private fun menuItem(label: String, onClick: Action) = JMenuItem(label).apply { addActionListener { onClick() } }

    private fun menuHeader(label: String) = JMenuItem(label).apply { isEnabled = false }

    private fun promptInput(text: String) = JOptionPane.showInputDialog(text)

    private fun getHeaderText(elementName: String) = "Actions for element $elementName"
}
