package com.github.leomartins1999.xmlify

data class AppConfiguration(
    val elementProviderClass: String? = System.getenv("xmlify.elementProviderClass"),
    val actionProviderClass: String? = System.getenv("xmlify.actionProviderClass"),
    val defaultFileExtension: String = System.getenv("xmlify.defaultFileExtension") ?: "xml"
)
