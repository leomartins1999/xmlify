package com.github.leomartins1999.xmlify.view

import com.github.leomartins1999.xmlify.Controller
import com.github.leomartins1999.xmlify.model.ElementObserver
import com.github.leomartins1999.xmlify.model.ElementType
import com.github.leomartins1999.xmlify.model.ModelElement
import com.github.leomartins1999.xmlify.model.ModelLeafElement
import com.github.leomartins1999.xmlify.utils.Action
import javax.swing.JMenuItem
import javax.swing.JPopupMenu

class ElementPopupMenu(
    private val controller: Controller,
    private val element: ModelElement<*>
) : JPopupMenu(), ElementObserver {

    private val menuHeader = menuHeader(getHeaderText(element.element.name))
    private val customActionsHeader = menuHeader(getCustomActionsHeaderText(element.element.name))

    init {
        add(menuHeader)

        add(buildAttributeActionsHeader())
        add(buildAddAttributeAction())
        add(buildUpdateAttributeAction())
        add(buildDeleteAttributeAction())

        add(buildElementActionsHeader())
        add(buildUpdateNameAction())
        add(buildExtractElementAction())

        if (element is ModelLeafElement) {
            add(buildUpdateValueAction())
        } else {
            add(buildAddElementAction())
        }

        add(buildDeleteElementAction())

        val customActions = getCustomActions()
        if (customActions.isNotEmpty()) {
            add(customActionsHeader)
            customActions.forEach { add(it) }
        }

        element.subscribe(this)
    }

    override fun onUpdateName(newName: String) {
        menuHeader.text = getHeaderText(newName)
        customActionsHeader.text = getCustomActionsHeaderText(newName)
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

    private fun buildExtractElementAction() = menuItem("Export element to file") {
        val filename = promptInput("Filename")
        controller.exportToFile(element.elementId, filename)
    }

    private fun buildUpdateValueAction() = menuItem("Update value") {
        val newValue = promptInput("New value")
        controller.updateValue(element.elementId, newValue)
    }

    private fun buildAddElementAction() = menuItem("Add element") {
        val elementType = promptInput("Element type (must be 'Leaf' or 'Tree')")
        val elementName = promptInput("Element name")
        controller.addElement(element.elementId, ElementType.valueOf(elementType), elementName)
    }

    private fun buildDeleteElementAction() = menuItem("Delete element") {
        controller.deleteElement(element.elementId)
    }

    private fun getCustomActions() = actionRegistry
        .getActions(element.element.name)
        .map { menuItem(it.actionName) { it.action(controller, element.elementId) } }

    private fun menuItem(label: String, onClick: Action) = JMenuItem(label).apply { addActionListener { onClick() } }

    private fun menuHeader(label: String) = JMenuItem(label).apply { isEnabled = false }

    private fun getHeaderText(elementName: String) = "Actions for element $elementName"
    private fun getCustomActionsHeaderText(elementName: String) = "Custom actions for element $elementName"

    private companion object {
        private val actionRegistry = ElementActionRegistry()
    }
}
