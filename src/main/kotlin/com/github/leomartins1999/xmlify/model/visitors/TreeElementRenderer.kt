package com.github.leomartins1999.xmlify.model.visitors

import com.github.leomartins1999.xmlify.model.LeafElement
import com.github.leomartins1999.xmlify.model.TreeElement
import com.github.leomartins1999.xmlify.model.collapsedElementTemplate
import com.github.leomartins1999.xmlify.model.treeElementEndTemplate
import com.github.leomartins1999.xmlify.model.treeElementStartTemplate

const val newline = "\n"
const val tab = "\t"

/**
 * This object is stateful!
 * Make you sure you use this only once
 * per TreeElement
 */
class TreeElementRenderer(
    private val treeElement: TreeElement
) : ElementVisitor {

    private val output = mutableListOf<String>()
    private var depth = 0

    fun render(): String {
        treeElement.accept(this)

        return output.joinToString(separator = newline)
    }

    override fun visit(element: LeafElement) {
        appendToOutput(element.render())
    }

    override fun visit(element: TreeElement) =
        if (element.hasChildren()) {
            appendToOutput(treeElementStartTemplate(element))
            depth++
            true
        } else {
            appendToOutput(collapsedElementTemplate(element))
            false
        }

    override fun endVisit(element: TreeElement) {
        depth--
        appendToOutput(treeElementEndTemplate(element))
    }

    private fun appendToOutput(text: String) = output.add(getIndentation() + text)

    private fun getIndentation() = tab.repeat(depth)
}
