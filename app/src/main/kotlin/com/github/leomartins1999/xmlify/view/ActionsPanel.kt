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

        add(buildUndoButton(controller))
    }

    private fun buildUndoButton(controller: Controller) =
        JButton(undoText).apply { addActionListener { controller.undo() } }

    private companion object {
        private const val undoText = "Undo"
    }

}
