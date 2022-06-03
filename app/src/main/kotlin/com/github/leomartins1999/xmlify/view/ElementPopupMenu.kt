package com.github.leomartins1999.xmlify.view

import com.github.leomartins1999.xmlify.Controller
import com.github.leomartins1999.xmlify.model.ElementObserver
import com.github.leomartins1999.xmlify.model.ModelElement
import javax.swing.JMenuItem
import javax.swing.JOptionPane
import javax.swing.JPopupMenu

class ElementPopupMenu(
    private val controller: Controller,
    private val element: ModelElement<*>
) : JPopupMenu(), ElementObserver {

    private val menuHeader = JMenuItem(getHeaderText(element.element.name)).apply { isEnabled = false }

    private val menuActions = mapOf(
        configureChangeName()
    )

    init {
        add(menuHeader)
        configureMenuActions()

        element.subscribe(this)
    }

    override fun onChangeName(newName: String) {
        menuHeader.text = getHeaderText(newName)
    }

    private fun configureMenuActions() {
        menuActions.forEach { (actionName, onClick) ->
            val item = JMenuItem(actionName)
            item.addActionListener { onClick() }
            add(item)
        }
    }

    private fun configureChangeName() = "Change element name" to {
        val newName = promptInput("New element name")
        controller.updateName(element.elementId, newName)
    }

    private fun promptInput(text: String) = JOptionPane.showInputDialog(text)

    private fun getHeaderText(elementName: String) = "Actions for $elementName"
}
