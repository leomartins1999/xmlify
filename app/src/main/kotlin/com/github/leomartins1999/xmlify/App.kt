package com.github.leomartins1999.xmlify

import com.github.leomartins1999.xmlify.model.Model
import com.github.leomartins1999.xmlify.view.View

fun main() {
    val model = Model()
    val controller = Controller(model)
    val view = View(controller)

    with(view) { start() }
}
