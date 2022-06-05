package com.github.leomartins1999.xmlify.view

import com.github.leomartins1999.xmlify.Controller
import javax.swing.BoxLayout
import javax.swing.BoxLayout.X_AXIS
import javax.swing.JButton
import javax.swing.JPanel

class ActionsPanel(
    controller: Controller
) : JPanel() {

    init {
        layout = BoxLayout(this, X_AXIS)

        add(buildButton(undoText) { controller.undo() })
        add(buildButton(redoText) { controller.redo() })
    }

    private fun buildButton(label: String, onClick: Action) =
        JButton(label).apply { addActionListener { onClick() } }

    private companion object {
        private const val undoText = "Undo"
        private const val redoText = "Redo"
    }
}
