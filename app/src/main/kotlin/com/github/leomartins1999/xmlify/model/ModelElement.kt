package com.github.leomartins1999.xmlify.model

import com.github.leomartins1999.xmlify.exceptions.InvalidModelOperationException
import com.github.leomartins1999.xmlify.exceptions.UnknownElementTypeException

abstract class ModelElement<T : Element>(
    val elementId: ElementID,
    var parentElementId: ElementID
) {

    private val observers = mutableListOf<ElementObserver>()

    abstract var element: T
        internal set

    fun updateName(newName: String) {
        element = element.copyElement(name = newName) as T

        notifyObservers { onUpdateName(newName) }
    }

    fun addAttribute(key: String, value: String) {
        if (element.attributes.containsKey(key)) throw InvalidModelOperationException("Trying to add attribute $key that already exists")

        val attributesCopy = element.attributes.toMutableMap()
        attributesCopy[key] = value

        element = element.copyElement(attributes = attributesCopy) as T
        notifyObservers { onAddAttribute(key, value) }
    }

    fun updateAttribute(key: String, value: String) {
        if (!element.attributes.containsKey(key)) throw InvalidModelOperationException("Trying to update attribute $key that does not exist")

        val attributesCopy = element.attributes.toMutableMap()
        attributesCopy[key] = value

        element = element.copyElement(attributes = attributesCopy) as T

        notifyObservers { onUpdateAttribute(key, value) }
    }

    fun deleteAttribute(key: String) {
        if (!element.attributes.containsKey(key)) throw InvalidModelOperationException("Trying to delete attribute $key that does not exist")

        val attributesCopy = element.attributes.toMutableMap()
        attributesCopy.remove(key)

        element = element.copyElement(attributes = attributesCopy) as T

        notifyObservers { onDeleteAttribute(key) }
    }

    fun subscribe(consumer: ElementObserver) = observers.add(consumer)

    fun notifyObservers(notification: ElementObserver.() -> Unit) = observers.forEach(notification)
}

fun Element.toModelElement(
    id: ElementID,
    previousElementId: ElementID
) = when (this) {
    is LeafElement -> ModelLeafElement(this, id, previousElementId)
    is TreeElement -> ModelTreeElement(this, id, previousElementId)
    else -> throw UnknownElementTypeException(this::class)
}
