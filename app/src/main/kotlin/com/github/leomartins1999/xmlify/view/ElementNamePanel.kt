package com.github.leomartins1999.xmlify.view

import javax.swing.BorderFactory.createTitledBorder
import javax.swing.JLabel
import javax.swing.JPanel

class ElementNamePanel(private val elementName: String) : JPanel() {

    init {
        render()
    }

    private fun render() {
        border = panelBorder

        add(JLabel(elementName))
    }

    private companion object {
        private val panelBorder = createTitledBorder("Name")
    }
}
