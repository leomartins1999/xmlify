package com.github.leomartins1999.xmlify

import com.github.leomartins1999.xmlify.model.ElementID
import com.github.leomartins1999.xmlify.model.Model

class Controller(private val model: Model) {

    fun getRoot() = model.root

    fun getChildren(elementId: ElementID) = model.getChildren(elementId)

    fun updateName(elementId: ElementID, newName: String) = model.updateName(elementId, newName)

    fun addAttribute(elementId: ElementID, key: String, value: String) =
        model.addAttribute(elementId, key, value)

    fun updateAttribute(elementId: ElementID, key: String, value: String) =
        model.updateAttribute(elementId, key, value)

    fun deleteAttribute(elementId: ElementID, key: String) =
        model.deleteAttribute(elementId, key)

    fun addElement(parentElementID: ElementID, elementType: String, elementName: String) =
        model.addElement(parentElementID, elementType, elementName)

    fun getElement(elementId: ElementID) = model.getElement(elementId)

    fun deleteElement(elementId: ElementID) =
        model.deleteElement(elementId)

    fun updateValue(elementId: ElementID, newValue: Any) =
        model.updateValue(elementId, newValue)
}
