package com.github.leomartins1999.xmlify.view

import com.github.leomartins1999.xmlify.Controller
import com.github.leomartins1999.xmlify.model.ElementObserver
import com.github.leomartins1999.xmlify.model.ModelElement
import com.github.leomartins1999.xmlify.model.ModelLeafElement
import com.github.leomartins1999.xmlify.model.ModelTreeElement
import javax.swing.JMenuItem
import javax.swing.JOptionPane
import javax.swing.JPopupMenu

class ElementPopupMenu(
    private val controller: Controller,
    private val element: ModelElement<*>
) : JPopupMenu(), ElementObserver {

    private val menuHeader = JMenuItem(getHeaderText(element.element.name)).apply { isEnabled = false }

    private val menuActions = mapOf(
        configureChangeName(),
        configureAddAttribute(),
        configureUpdateAttribute(),
        configureDeleteAttribute(),
        configureDeleteElement()
    )

    init {
        add(menuHeader)

        configureMenuActions()

        if (element is ModelLeafElement) configureLeafElementMenuActions()
        else if (element is ModelTreeElement) configureTreeElementMenuActions()

        element.subscribe(this)
    }

    override fun onUpdateName(newName: String) {
        menuHeader.text = getHeaderText(newName)
    }

    private fun configureMenuActions() = menuActions
        .forEach { (actionName, onClick) ->
            val item = JMenuItem(actionName)
            item.addActionListener { onClick() }
            add(item)
        }

    private fun configureLeafElementMenuActions() {
        val item = JMenuItem("Update value")
        item.addActionListener {
            val newValue = promptInput("New value")
            controller.updateValue(element.elementId, newValue)
        }

        add(item)
    }

    private fun configureTreeElementMenuActions() {
        val item = JMenuItem("Add element")
        item.addActionListener {
            val elementType = promptInput("Element type (must be 'leaf' or 'tree')")
            val elementName = promptInput("Element name")
            controller.addElement(element.elementId, elementType, elementName)
        }

        add(item)
    }

    private fun configureChangeName() = "Change element name" to {
        val newName = promptInput("New element name")
        controller.updateName(element.elementId, newName)
    }

    private fun configureAddAttribute() = "Add Attribute" to {
        val key = promptInput("Attribute key")
        val value = promptInput("Attribute value")
        controller.addAttribute(element.elementId, key, value)
    }

    private fun configureUpdateAttribute() = "Update Attribute" to {
        val key = promptInput("Attribute key")
        val value = promptInput("Attribute value")
        controller.updateAttribute(element.elementId, key, value)
    }

    private fun configureDeleteAttribute() = "Delete Attribute" to {
        val key = promptInput("Attribute key")
        controller.deleteAttribute(element.elementId, key)
    }

    private fun configureDeleteElement() = "Delete Element" to {
        controller.deleteElement(element.elementId)
    }

    private fun promptInput(text: String) = JOptionPane.showInputDialog(text)

    private fun getHeaderText(elementName: String) = "Actions for $elementName"
}
