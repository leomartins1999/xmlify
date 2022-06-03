package com.github.leomartins1999.xmlify.view

import com.github.leomartins1999.xmlify.model.TreeElement
import javax.swing.BorderFactory.createTitledBorder
import javax.swing.BoxLayout
import javax.swing.JPanel

class TreeElementContentPanel(private val treeElement: TreeElement) : JPanel() {

    init {
        render()
    }

    private fun render() {
        layout = BoxLayout(this, BoxLayout.PAGE_AXIS)
        border = panelBorder

        treeElement.children.forEach { add(ElementView(it)) }
    }

    private companion object {
        private val panelBorder = createTitledBorder("Children")
    }
}
