package com.github.leomartins1999.xmlify.view

import com.github.leomartins1999.xmlify.AppConfiguration
import com.github.leomartins1999.xmlify.Controller
import com.github.leomartins1999.xmlify.model.ElementID
import com.github.leomartins1999.xmlify.utils.instanceClassWithName

class ElementAction(
    val actionName: String,
    val action: (Controller, ElementID) -> Unit
)

interface ElementActionProvider {
    fun getActions(elementName: String): List<ElementAction>
}

class ElementActionRegistry(
    appConfiguration: AppConfiguration = AppConfiguration()
) {

    private val provider = buildActionProvider(appConfiguration)
    private val actionCache = mutableMapOf<String, List<ElementAction>>()

    fun getActions(elementName: String): List<ElementAction> {
        if (provider == null) return listOf()
        if (actionCache.containsKey(elementName)) return actionCache[elementName]!!

        val actions = provider.getActions(elementName)
        actionCache[elementName] = actions
        return actions
    }

    private fun buildActionProvider(config: AppConfiguration) =
        config.actionProviderClass?.instanceClassWithName<ElementActionProvider>()
}
