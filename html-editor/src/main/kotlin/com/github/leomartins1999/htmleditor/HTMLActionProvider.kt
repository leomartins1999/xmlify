package com.github.leomartins1999.htmleditor

import com.github.leomartins1999.xmlify.model.ElementType
import com.github.leomartins1999.xmlify.view.ElementAction
import com.github.leomartins1999.xmlify.view.ElementActionProvider
import com.github.leomartins1999.xmlify.view.promptInput

class HTMLActionProvider : ElementActionProvider {

    override fun getActions(elementName: String) = when (elementName) {
        "body" -> getBodyCustomActions()
        "head" -> getHeadCustomActions()
        else -> emptyList()
    }

    private fun getBodyCustomActions() = listOf(
        addButtonAction(),
        addStylesheetAction()
    )

    private fun getHeadCustomActions() = listOf(
        specifyCharsetAction()
    )

    private fun addButtonAction() = ElementAction(
        actionName = "Add Button",
        action = { controller, elementID ->
            val newElementId = controller.addElement(
                elementID,
                ElementType.Leaf,
                "button"
            )

            controller.addAttribute(newElementId, "type", "button")
            controller.updateValue(newElementId, "Click me!")
        }
    )

    private fun addStylesheetAction() = ElementAction(
        actionName = "Add Stylesheet",
        action = { controller, i ->
            val stylesheetURL = promptInput("Stylesheet url")

            val newElementId = controller.addElement(i, ElementType.Leaf, "link")
            controller.addAttribute(newElementId, "rel", "stylesheet")
            controller.addAttribute(newElementId, "href", stylesheetURL)
        }
    )

    private fun specifyCharsetAction() = ElementAction(
        actionName = "Specify charset",
        action = { controller, i ->
            val newCharset = promptInput("New charset")

            val charsetElement = controller
                .getChildren(i)
                .firstOrNull { it.element.name == "meta" && it.element.attributes.containsKey("charset") }

            if (charsetElement == null) {
                val newElementId = controller.addElement(i, ElementType.Leaf, "meta")
                controller.addAttribute(newElementId, "charset", newCharset)
            } else {
                controller.updateAttribute(charsetElement.elementId, "charset", newCharset)
            }
        }
    )

}
