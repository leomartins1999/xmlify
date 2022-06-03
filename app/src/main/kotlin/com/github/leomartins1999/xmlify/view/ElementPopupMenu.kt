package com.github.leomartins1999.xmlify.view

import javax.swing.JMenuItem
import javax.swing.JOptionPane
import javax.swing.JPopupMenu

class ElementPopupMenu(private val elementName: String) : JPopupMenu() {

    private val menuActions = mapOf(
        configureChangeName()
    )

    init {
        configureMenuHeader()
        configureMenuActions()
    }

    private fun configureMenuHeader() {
        val item = JMenuItem("Actions for $elementName")
        item.isEnabled = false

        add(item)
    }

    private fun configureMenuActions() {
        menuActions.forEach { (actionName, onClick) ->
            val item = JMenuItem(actionName)
            item.addActionListener { onClick() }
            add(item)
        }
    }

    private fun configureChangeName() = "Change element name" to {
        val text = promptInput("New element name")

        println(text)
    }

    private fun promptInput(text: String) = JOptionPane.showInputDialog(text)

}
