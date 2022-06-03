package com.github.leomartins1999.xmlify.view

import com.github.leomartins1999.xmlify.Controller
import java.awt.Dimension
import javax.swing.JFrame

class View(controller: Controller) : JFrame(windowTitle) {

    init {
        defaultCloseOperation = EXIT_ON_CLOSE
        size = windowDimension

        add(ElementView(controller, controller.getRoot()))
    }

    fun start() {
        isVisible = true
    }

    private companion object {
        private const val windowTitle = "xmlify-app"
        private val windowDimension = Dimension(300, 300)
    }
}
