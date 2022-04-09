package com.github.leomartins1999.xmlfordummies.xml

const val newline = "\n"
const val tab = "\t"

/**
 * This object is stateful!
 * Make you sure you use this only once
 * per TreeElement
 */
class TreeElementRenderer : ElementVisitor {

    private val output = mutableListOf<String>()
    private var depth = 0

    fun render(treeElement: TreeElement): String {
        treeElement.accept(this)

        return output.joinToString(separator = newline)
    }

    override fun visit(element: LeafElement) {
        appendToOutput(element.render())
    }

    override fun visit(element: TreeElement) =
        if (element.hasChildren()) {
            appendToOutput(treeElementStartTemplate(element.name))
            depth += 1
            true
        } else {
            appendToOutput(collapsedElementTemplate(element.name))
            false
        }

    override fun endVisit(element: TreeElement) {
        depth -= 1
        appendToOutput(treeElementEndTemplate(element.name))
    }

    private fun appendToOutput(text: String) = output.add(generateIndentation() + text)

    private fun generateIndentation() = tab.repeat(depth)

}
