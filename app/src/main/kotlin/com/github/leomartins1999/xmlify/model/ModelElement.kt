package com.github.leomartins1999.xmlify.model

import com.github.leomartins1999.xmlify.UnknownElementTypeException

abstract class ModelElement<T : Element>(
    val elementId: ElementID,
    val previousElementId: ElementID
) {

    private val observers = mutableListOf<ElementObserver>()

    abstract var element: T
        internal set

    fun updateName(newName: String) {
        element = element.copyElement(name = newName) as T

        notifyObserver { onChangeName(newName) }
    }

    fun subscribe(consumer: ElementObserver) = observers.add(consumer)

    private fun notifyObserver(notification: ElementObserver.() -> Unit) = observers.forEach(notification)
}

class ModelLeafElement(
    originalElement: LeafElement,
    id: ElementID,
    previousElementId: ElementID
) : ModelElement<LeafElement>(id, previousElementId) {
    override var element = originalElement.copyElement()
}

class ModelTreeElement(
    originalElement: TreeElement,
    id: ElementID,
    previousElementId: ElementID
) : ModelElement<TreeElement>(id, previousElementId) {
    override var element = originalElement.copyElement()
}

fun Element.toModelElement(
    id: ElementID,
    previousElementId: ElementID
) = when (this) {
    is LeafElement -> ModelLeafElement(this, id, previousElementId)
    is TreeElement -> ModelTreeElement(this, id, previousElementId)
    else -> throw UnknownElementTypeException(this::class)
}
