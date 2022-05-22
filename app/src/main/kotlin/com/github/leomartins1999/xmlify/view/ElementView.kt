package com.github.leomartins1999.xmlify.view

import com.github.leomartins1999.xmlify.model.Element
import java.awt.Color
import java.awt.Font
import java.awt.Graphics
import javax.swing.BorderFactory.createEmptyBorder
import javax.swing.BorderFactory.createLineBorder
import javax.swing.JPanel
import javax.swing.border.CompoundBorder

class ElementView(
    private val element: Element
) : JPanel() {

    init {
        render()
    }

    private fun render() {
        border = elementBorder
    }

    override fun paintComponent(g: Graphics) {
        super.paintComponent(g)

        paintTitle(g)
        paintAttributes(g)
    }

    private fun paintTitle(g: Graphics) {
        g.font = titleFont
        g.drawString(element.name, 10, 20)
    }

    private fun paintAttributes(g: Graphics) {
        g.font = titleFont
        g.drawString("Attributes:", 20, 50)

        element.attributes.onEachIndexed { idx, (k, v) ->
            g.font = textFont
            g.drawString("$k: $v", 20, 65 + idx * 12)
        }
    }

    private companion object {
        private val elementBorder = CompoundBorder(
            createEmptyBorder(30, 10, 10, 10),
            createLineBorder(Color.BLUE, 2, true)
        )
        private val titleFont = Font("Arial", Font.BOLD, 16)
        private val textFont = Font("Arial", Font.PLAIN, 12)
    }

}
