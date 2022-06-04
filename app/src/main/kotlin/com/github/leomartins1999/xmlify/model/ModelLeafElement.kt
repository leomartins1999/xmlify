package com.github.leomartins1999.xmlify.model

class ModelLeafElement(
    originalElement: LeafElement,
    id: ElementID,
    previousElementId: ElementID
) : ModelElement<LeafElement>(id, previousElementId) {
    override var element = originalElement.copyElement()
}
