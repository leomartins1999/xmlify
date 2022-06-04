package com.github.leomartins1999.xmlify.model

import com.github.leomartins1999.xmlify.exceptions.ElementNotFoundException
import com.github.leomartins1999.xmlify.exceptions.InvalidModelOperationException
import java.util.ArrayDeque
import java.util.concurrent.atomic.AtomicInteger

typealias ElementID = Int
typealias ModelAction = () -> Unit

class Model(
    root: Element = defaultElement
) {

    private val idGenerator = AtomicInteger(initialId)

    private val store = mutableMapOf<ElementID, ModelElement<*>>()

    private val undoStack = ArrayDeque<ModelAction>()

    val root by lazy { store[initialId]!! }

    init {
        initElementStore(root)
    }

    fun getChildren(elementId: ElementID): List<ModelElement<*>> = store
        .values
        .filter { it.parentElementId == elementId }

    fun getElement(elementId: ElementID) = store[elementId] ?: throw ElementNotFoundException(elementId)

    fun updateName(elementId: ElementID, newName: String, isUndo: Boolean = false) {
        val elem = store[elementId] ?: throw ElementNotFoundException(elementId)
        val oldName = elem.element.name

        elem.updateName(newName)

        if (!isUndo) enqueueForUndo { updateName(elementId, oldName, true) }
    }

    fun addAttribute(
        elementId: ElementID,
        key: String,
        value: String,
        isUndo: Boolean = false
    ) = store[elementId]
        ?.addAttribute(key, value)
        ?.also { if (!isUndo) enqueueForUndo { deleteAttribute(elementId, key, true) } }
        ?: throw ElementNotFoundException(elementId)

    fun updateAttribute(
        elementId: ElementID,
        key: String,
        value: String,
        isUndo: Boolean = false
    ) {
        val elem = store[elementId] ?: throw ElementNotFoundException(elementId)
        val oldValue = elem.element.attributes[key]!!

        elem.updateAttribute(key, value)

        if (!isUndo) enqueueForUndo { updateAttribute(elementId, key, oldValue, true) }
    }

    fun deleteAttribute(elementId: ElementID, key: String, isUndo: Boolean = false) {
        val elem = store[elementId] ?: throw ElementNotFoundException(elementId)
        val oldValue = elem.element.attributes[key]!!

        elem.deleteAttribute(key)

        if (!isUndo) enqueueForUndo { addAttribute(elementId, key, oldValue, true) }
    }

    fun addElement(
        parentElementId: ElementID,
        elementType: String,
        elementName: String,
        isUndo: Boolean = false
    ) {
        val parent = store[parentElementId] ?: throw ElementNotFoundException(parentElementId)

        val element = when (elementType) {
            "tree" -> element(elementName, listOf())
            "leaf" -> element(elementName)
            else -> throw InvalidModelOperationException("Unknown element type $elementType")
        }

        val modelElement = appendToStore(element, parentElementId)

        parent as ModelTreeElement
        parent.addChild(modelElement.elementId, modelElement.element)

        if (!isUndo) enqueueForUndo { deleteElement(modelElement.elementId, true) }
    }

    fun deleteElement(elementId: ElementID, isUndo: Boolean = false) {
        if (elementId == initialId) throw InvalidModelOperationException("Root element cannot be removed!")

        val elem = store[elementId] ?: throw ElementNotFoundException(elementId)
        val parent = store[elem.parentElementId] ?: throw ElementNotFoundException(elem.parentElementId)

        parent as ModelTreeElement
        parent.removeChild(elementId, elem.element)

        if (!isUndo) enqueueForUndo { appendElementToTree(elem.elementId, parent.elementId) }
    }

    fun updateValue(elementId: ElementID, newValue: Any?, isUndo: Boolean = false) {
        val elem = store[elementId] ?: throw ElementNotFoundException(elementId)
        elem as ModelLeafElement

        val oldValue = elem.element.value
        elem.updateValue(newValue)

        if (!isUndo) enqueueForUndo { updateValue(elementId, oldValue, true) }
    }

    fun undo() =
        if (undoStack.isNotEmpty()) undoStack.pop().invoke()
        else throw InvalidModelOperationException("Tried to undo but there is nothing to undo!")

    private fun enqueueForUndo(action: ModelAction) = undoStack.push(action)

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

    private fun appendElementToTree(elementId: ElementID, parentElementId: ElementID) {
        val elem = store[elementId] ?: throw ElementNotFoundException(elementId)
        val parent = store[parentElementId] ?: throw ElementNotFoundException(parentElementId)

        parent as ModelTreeElement
        parent.addChild(elem.elementId, elem.element)
    }

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
