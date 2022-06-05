package com.github.leomartins1999.xmlify

data class AppConfiguration(
    val elementProviderClass: String? = System.getenv("xmlify.elementProviderClass")
)
