package com.github.leomartins1999.xmlify.view

import com.github.leomartins1999.xmlify.model.Element
import com.github.leomartins1999.xmlify.model.LeafElement
import com.github.leomartins1999.xmlify.model.TreeElement
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
    private val element: Element
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

    private fun renderNamePanel() = add(ElementNamePanel(element.name))

    private fun renderAttributesPanel() = add(ElementAttributesPanel(element.attributes))

    private fun renderContentPanel() = when (element) {
        is LeafElement -> add(LeafElementContentPanel(element))
        is TreeElement -> add(TreeElementContentPanel(element))
        else -> throw Error("Unexpected element type provided ${element::class.simpleName}!")
    }

    private fun configurePopupMenu() {
        val popup = ElementPopupMenu(element.name)

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
