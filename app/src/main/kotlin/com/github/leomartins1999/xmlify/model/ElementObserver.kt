package com.github.leomartins1999.xmlify.model

interface ElementObserver {
    fun onUpdateName(newName: String) {}

    fun onAddAttribute(key: String, value: String) {}

    fun onUpdateAttribute(key: String, value: String) {}

    fun onDeleteAttribute(key: String) {}

    fun onElementRemoved(elementID: ElementID) {}
}
