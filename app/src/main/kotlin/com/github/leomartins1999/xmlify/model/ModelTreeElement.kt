package com.github.leomartins1999.xmlify.model

class ModelTreeElement(
    originalElement: TreeElement,
    id: ElementID,
    previousElementId: ElementID
) : ModelElement<TreeElement>(id, previousElementId) {
    override var element = originalElement.copyElement()

    fun addChild(elementID: ElementID, elementToAdd: Element) {
        val updatedChildren = element.children + elementToAdd
        element = element.copy(children = updatedChildren)
        notifyObservers { onElementAdded(elementID) }
    }

    fun removeChild(elementID: ElementID, elementToRemove: Element) {
        val updatedChildren = element.children - elementToRemove
        element = element.copy(children = updatedChildren)
        notifyObservers { onElementRemoved(elementID) }
    }
}
