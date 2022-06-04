package com.github.leomartins1999.xmlify.model

interface ElementObserver {
    fun onUpdateName(newName: String) {}

    fun onAddAttribute(key: String, value: String) {}

    fun onUpdateAttribute(key: String, value: String) {}

    fun onDeleteAttribute(key: String) {}

    fun onElementAdded(element: ElementID) {}

    fun onElementRemoved(elementID: ElementID) {}

    fun onUpdateValue(newValue: Any) {}
}
