package com.github.leomartins1999.xmlify.view

import com.github.leomartins1999.xmlify.model.ElementObserver
import com.github.leomartins1999.xmlify.model.ModelLeafElement
import javax.swing.BorderFactory.createTitledBorder
import javax.swing.JLabel
import javax.swing.JPanel

class LeafElementContentPanel(leafElement: ModelLeafElement) : JPanel(), ElementObserver {

    private val valueLabel = JLabel(leafElement.element.value.toString())

    init {
        border = panelBorder

        add(valueLabel)

        leafElement.subscribe(this)
    }

    override fun onUpdateValue(newValue: Any) {
        valueLabel.text = newValue.toString()
    }

    private companion object {
        private val panelBorder = createTitledBorder("Content")
    }
}
