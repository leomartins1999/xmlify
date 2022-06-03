package com.github.leomartins1999.xmlify.view

import com.github.leomartins1999.xmlify.model.ModelLeafElement
import javax.swing.BorderFactory.createTitledBorder
import javax.swing.JLabel
import javax.swing.JPanel

class LeafElementContentPanel(private val leafElement: ModelLeafElement) : JPanel() {

    init {
        render()
    }

    private fun render() {
        border = panelBorder

        add(JLabel(leafElement.element.value.toString()))
    }

    private companion object {
        private val panelBorder = createTitledBorder("Content")
    }
}
