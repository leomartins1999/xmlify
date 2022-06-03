package com.github.leomartins1999.xmlify.view

import com.github.leomartins1999.xmlify.Controller
import com.github.leomartins1999.xmlify.model.ModelTreeElement
import javax.swing.BorderFactory.createTitledBorder
import javax.swing.BoxLayout
import javax.swing.JPanel

class TreeElementContentPanel(
    private val controller: Controller,
    treeElement: ModelTreeElement
) : JPanel() {

    init {
        layout = BoxLayout(this, BoxLayout.PAGE_AXIS)
        border = panelBorder

        controller
            .getChildren(treeElement.elementId)
            .forEach { add(ElementView(controller, it)) }
    }

    private companion object {
        private val panelBorder = createTitledBorder("Children")
    }
}
