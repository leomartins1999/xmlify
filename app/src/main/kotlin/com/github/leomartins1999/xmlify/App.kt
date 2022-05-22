package com.github.leomartins1999.xmlify

import com.github.leomartins1999.xmlify.model.Model
import com.github.leomartins1999.xmlify.view.View

fun main() {
    val model = Model()
    val view = View(model)

    with(view) { start() }
}
