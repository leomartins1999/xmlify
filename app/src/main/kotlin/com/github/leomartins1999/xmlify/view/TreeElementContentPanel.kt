package com.github.leomartins1999.xmlify.view

import com.github.leomartins1999.xmlify.Controller
import com.github.leomartins1999.xmlify.model.ElementID
import com.github.leomartins1999.xmlify.model.ElementObserver
import com.github.leomartins1999.xmlify.model.ModelTreeElement
import javax.swing.BorderFactory.createTitledBorder
import javax.swing.BoxLayout
import javax.swing.JPanel

class TreeElementContentPanel(
    private val controller: Controller,
    treeElement: ModelTreeElement
) : JPanel(), ElementObserver {

    private val elementPanels = mutableMapOf<ElementID, JPanel>()

    init {
        layout = BoxLayout(this, BoxLayout.PAGE_AXIS)
        border = panelBorder

        controller
            .getChildren(treeElement.elementId)
            .forEach {
                val panel = ElementView(controller, it)
                elementPanels[it.elementId] = panel
                add(panel)
            }

        treeElement.subscribe(this)
    }

    override fun onElementAdded(element: ElementID) {
        val elementModel = controller.getElement(element)
        val elementView = ElementView(controller, elementModel)

        elementPanels[element] = elementView
        add(elementView)
        revalidate()
    }

    override fun onElementRemoved(elementID: ElementID) =
        remove(elementPanels[elementID]).also { revalidate() }

    private companion object {
        private val panelBorder = createTitledBorder("Children")
    }
}
