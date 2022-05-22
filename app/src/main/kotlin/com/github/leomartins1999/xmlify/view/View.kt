package com.github.leomartins1999.xmlify.view

import com.github.leomartins1999.xmlify.model.Model
import java.awt.Dimension
import javax.swing.JFrame

class View(
    private val model: Model
) : JFrame(windowTitle) {

    init {
        render()
    }

    fun start() {
        isVisible = true
    }

    private fun render() {
        defaultCloseOperation = EXIT_ON_CLOSE
        size = windowDimension

        renderElement()
    }

    private fun renderElement() {
        val view = ElementView(model.element)
        add(view)
    }

    private companion object {
        private const val windowTitle = "xmlify-app"
        private val windowDimension = Dimension(300, 300)
    }
}
