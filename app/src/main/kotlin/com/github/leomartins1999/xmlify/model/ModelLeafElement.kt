package com.github.leomartins1999.xmlify.model

class ModelLeafElement(
    originalElement: LeafElement,
    id: ElementID,
    previousElementId: ElementID
) : ModelElement<LeafElement>(id, previousElementId) {
    override var element = originalElement.copyElement()

    fun updateValue(newValue: Any?) {
        element = element.copy(value = newValue)
        notifyObservers { onUpdateValue(newValue) }
    }
}
