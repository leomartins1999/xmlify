package com.github.leomartins1999.xmlify.model.visitors

import com.github.leomartins1999.xmlify.model.Element
import com.github.leomartins1999.xmlify.model.LeafElement
import com.github.leomartins1999.xmlify.model.TreeElement
import com.github.leomartins1999.xmlify.model.collapsedElementTemplate
import com.github.leomartins1999.xmlify.model.leafElementTemplate
import com.github.leomartins1999.xmlify.model.treeElementEndTemplate
import com.github.leomartins1999.xmlify.model.treeElementStartTemplate

private const val newline = "\n"
private const val tab = "\t"

internal class ElementRenderer(
    private val element: Element
) : ElementVisitor {

    private val output = mutableListOf<String>()
    private var depth = 0

    fun render(): String {
        element.accept(this)

        return output.joinToString(separator = newline)
    }

    override fun visit(element: LeafElement) {
        val str =
            if (element.value != null) leafElementTemplate(element)
            else collapsedElementTemplate(element)

        appendToOutput(str)
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
