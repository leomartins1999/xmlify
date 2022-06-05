package com.github.leomartins1999.xmlify.model

import com.github.leomartins1999.xmlify.exceptions.ElementNotFoundException
import com.github.leomartins1999.xmlify.exceptions.InvalidModelOperationException
import com.github.leomartins1999.xmlify.exceptions.UnknownElementTypeException
import com.github.leomartins1999.xmlify.utils.Action
import java.util.ArrayDeque
import java.util.concurrent.atomic.AtomicInteger

typealias ElementID = Int

enum class ElementType { Leaf, Tree }

class Model(
    root: Element = defaultElement
) {

    private val idGenerator = AtomicInteger(initialId)

    private val store = mutableMapOf<ElementID, ModelElement<*>>()

    private val undoStack = ArrayDeque<Action>()
    private val redoStack = ArrayDeque<Action>()

    val root by lazy { store[initialId]!! }

    init {
        initElementStore(root)
    }

    fun getChildren(elementId: ElementID): List<ModelElement<*>> = store
        .values
        .filter { it.parentElementId == elementId }

    fun getElement(elementId: ElementID) = store[elementId] ?: throw ElementNotFoundException(elementId)

    fun updateName(
        elementId: ElementID,
        newName: String,
        isUndo: Boolean = false,
        isRedo: Boolean = false
    ) {
        val elem = store[elementId] ?: throw ElementNotFoundException(elementId)
        val oldName = elem.element.name

        elem.updateName(newName)

        updateActionHistory(isUndo, isRedo) { undo, redo -> updateName(elementId, oldName, undo, redo) }
    }

    fun addAttribute(
        elementId: ElementID,
        key: String,
        value: String,
        isUndo: Boolean = false,
        isRedo: Boolean = false
    ) = store[elementId]
        ?.addAttribute(key, value)
        ?.also { updateActionHistory(isUndo, isRedo) { undo, redo -> deleteAttribute(elementId, key, undo, redo) } }
        ?: throw ElementNotFoundException(elementId)

    fun updateAttribute(
        elementId: ElementID,
        key: String,
        value: String,
        isUndo: Boolean = false,
        isRedo: Boolean = false
    ) {
        val elem = store[elementId] ?: throw ElementNotFoundException(elementId)
        val oldValue = elem.element.attributes[key]!!

        elem.updateAttribute(key, value)

        updateActionHistory(isUndo, isRedo) { undo, redo -> updateAttribute(elementId, key, oldValue, undo, redo) }
    }

    fun deleteAttribute(
        elementId: ElementID,
        key: String,
        isUndo: Boolean = false,
        isRedo: Boolean = false
    ) {
        val elem = store[elementId] ?: throw ElementNotFoundException(elementId)
        val oldValue = elem.element.attributes[key]!!

        elem.deleteAttribute(key)

        updateActionHistory(isUndo, isRedo) { undo, redo -> addAttribute(elementId, key, oldValue, undo, redo) }
    }

    fun addElement(
        parentElementId: ElementID,
        elementType: ElementType,
        elementName: String,
        isUndo: Boolean = false,
        isRedo: Boolean = false
    ): ElementID {
        val parent = store[parentElementId] ?: throw ElementNotFoundException(parentElementId)

        val element = when(elementType) {
            ElementType.Leaf -> element(elementName)
            ElementType.Tree -> element(elementName, listOf())
        }

        val modelElement = appendToStore(element, parentElementId)

        parent as ModelTreeElement
        parent.addChild(modelElement.elementId, modelElement.element)

        updateActionHistory(isUndo, isRedo) { undo, redo -> deleteElement(modelElement.elementId, undo, redo) }

        return modelElement.elementId
    }

    fun deleteElement(
        elementId: ElementID,
        isUndo: Boolean = false,
        isRedo: Boolean = false
    ) {
        if (elementId == initialId) throw InvalidModelOperationException("Root element cannot be removed!")

        val elem = store[elementId] ?: throw ElementNotFoundException(elementId)
        val parent = store[elem.parentElementId] ?: throw ElementNotFoundException(elem.parentElementId)

        parent as ModelTreeElement
        parent.removeChild(elementId, elem.element)
        elem.parentElementId = noParentElementId

        updateActionHistory(isUndo, isRedo) { undo, redo ->
            appendElementToTree(elem.elementId, parent.elementId, undo, redo)
        }
    }

    fun updateValue(
        elementId: ElementID,
        newValue: Any?,
        isUndo: Boolean = false,
        isRedo: Boolean = false
    ) {
        val elem = store[elementId] ?: throw ElementNotFoundException(elementId)
        elem as ModelLeafElement

        val oldValue = elem.element.value
        elem.updateValue(newValue)

        updateActionHistory(isUndo, isRedo) { undo, redo -> updateValue(elementId, oldValue, undo, redo) }
    }

    fun undo() =
        if (undoStack.isNotEmpty()) undoStack.pop().invoke()
        else throw InvalidModelOperationException("Tried to undo but there is nothing to undo!")

    fun redo() =
        if (redoStack.isNotEmpty()) redoStack.pop().invoke()
        else throw InvalidModelOperationException("Tried to redo but there is nothing to redo!")

    fun extractElements(elem: ModelElement<*> = root): Element {
        if (elem is ModelLeafElement) return elem.element
        if (elem !is ModelTreeElement) throw UnknownElementTypeException(elem::class)

        val children = getChildren(elem.elementId).map { extractElements(it) }
        return elem.element.copy(children = children)
    }

    private fun updateActionHistory(
        isUndo: Boolean,
        isRedo: Boolean,
        rollbackAction: (Boolean, Boolean) -> Unit
    ) =
        if (isUndo) enqueueForRedo { rollbackAction(false, true) }
        else enqueueForUndo(isRedo) { rollbackAction(true, false) }

    private fun enqueueForUndo(
        isRedo: Boolean = false,
        action: Action
    ) = undoStack
        .push(action)
        .also { if (!isRedo) redoStack.clear() }

    private fun enqueueForRedo(action: Action) = redoStack.push(action)

    private fun initElementStore(root: Element, previousElementId: ElementID = noParentElementId) {
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

    private fun appendElementToTree(
        elementId: ElementID,
        parentElementId: ElementID,
        isUndo: Boolean = false,
        isRedo: Boolean = false
    ) {
        val elem = store[elementId] ?: throw ElementNotFoundException(elementId)
        val parent = store[parentElementId] ?: throw ElementNotFoundException(parentElementId)

        parent as ModelTreeElement
        parent.addChild(elem.elementId, elem.element)

        elem.parentElementId = parentElementId

        updateActionHistory(isUndo, isRedo) { undo, redo -> deleteElement(elem.elementId, undo, redo) }
    }

    private companion object {
        private const val initialId = 1
        private const val noParentElementId = -1
    }
}
