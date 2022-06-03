package com.github.leomartins1999.xmlify

import com.github.leomartins1999.xmlify.model.ElementID
import com.github.leomartins1999.xmlify.model.Model

class Controller(private val model: Model) {

    fun getRoot() = model.root

    fun updateName(elementId: ElementID, newName: String) = model.updateName(elementId, newName)

    fun getChildren(elementId: ElementID) = model.getChildren(elementId)
}
