package com.github.leomartins1999.xmlify.model

import java.util.concurrent.atomic.AtomicInteger

typealias ElementID = Int

class Model(
    root: Element = defaultElement
) {

    private val idGenerator = AtomicInteger(initialId)

    private val store = mutableMapOf<ElementID, ModelElement<*>>()

    val root by lazy { store[initialId]!! }

    init {
        initElementStore(root)
    }

    fun updateName(elementId: ElementID, newName: String) = store[elementId]
        ?.updateName(newName)
        ?: throw NoSuchElementException()

    fun getChildren(elementId: ElementID): List<ModelElement<*>> = store
        .values
        .filter { it.previousElementId == elementId }

    private fun initElementStore(root: Element, previousElementId: ElementID = noPreviousElementId) {
        val modelElem = appendToStore(root, previousElementId)

        if (modelElem is ModelTreeElement) {
            modelElem.element.children.forEach { initElementStore(it, modelElem.elementId) }
        }
    }

    private fun appendToStore(elem: Element, previousElementId: ElementID): ModelElement<*> {
        val id = generateElementId()
        val modelElem = elem.toModelElement(id, previousElementId)

        store[id] = modelElem

        return modelElem
    }

    private fun generateElementId() = idGenerator.getAndIncrement()

    private companion object {
        private const val initialId = 1
        private const val noPreviousElementId = -1
    }
}

private val defaultElement = element(
    name = "myElement",
    attributes = mapOf("attr1" to "value1", "attr2" to "value2"),
    children = listOf(
        element("null"),
        element("string", "xpto"),
        element(
            "tree",
            children = listOf(
                element("leaf1", "l"),
                element("leaf2", "l")
            )
        )
    )
)
