package com.github.leomartins1999.xmlify.view

import com.github.leomartins1999.xmlify.Controller
import com.github.leomartins1999.xmlify.exceptions.UnknownElementTypeException
import com.github.leomartins1999.xmlify.model.ModelElement
import com.github.leomartins1999.xmlify.model.ModelLeafElement
import com.github.leomartins1999.xmlify.model.ModelTreeElement
import java.awt.Color
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.BorderFactory.createEmptyBorder
import javax.swing.BorderFactory.createLineBorder
import javax.swing.BoxLayout
import javax.swing.JPanel
import javax.swing.SwingUtilities.isRightMouseButton
import javax.swing.border.CompoundBorder

class ElementView(
    private val controller: Controller,
    private val element: ModelElement<*>
) : JPanel() {

    init {
        render()
    }

    private fun render() {
        layout = BoxLayout(this, BoxLayout.Y_AXIS)
        border = elementBorder

        renderNamePanel()
        renderAttributesPanel()
        renderContentPanel()

        configurePopupMenu()
    }

    private fun renderNamePanel() = add(ElementNamePanel(element))

    private fun renderAttributesPanel() = add(ElementAttributesPanel(element))

    private fun renderContentPanel() = when (element) {
        is ModelLeafElement -> add(LeafElementContentPanel(element))
        is ModelTreeElement -> add(TreeElementContentPanel(controller, element))
        else -> throw UnknownElementTypeException(element::class)
    }

    private fun configurePopupMenu() {
        val popup = ElementPopupMenu(controller, element)

        addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent) {
                if (isRightMouseButton(e)) popup.show(this@ElementView, e.x, e.y)
            }
        })
    }

    private companion object {
        private val elementBorder = CompoundBorder(
            createEmptyBorder(10, 10, 10, 10),
            createLineBorder(Color.BLUE, 2, true)
        )
    }
}
