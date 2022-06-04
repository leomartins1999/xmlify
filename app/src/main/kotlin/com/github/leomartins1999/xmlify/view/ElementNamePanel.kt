package com.github.leomartins1999.xmlify.view

import com.github.leomartins1999.xmlify.model.ElementObserver
import com.github.leomartins1999.xmlify.model.ModelElement
import javax.swing.BorderFactory.createTitledBorder
import javax.swing.JLabel
import javax.swing.JPanel

class ElementNamePanel(element: ModelElement<*>) : JPanel(), ElementObserver {

    private val label = JLabel(element.element.name)

    init {
        border = panelBorder
        add(label)

        element.subscribe(this)
    }

    override fun onUpdateName(newName: String) {
        label.text = newName
    }

    private companion object {
        private val panelBorder = createTitledBorder("Name")
    }
}
