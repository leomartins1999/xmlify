package com.github.leomartins1999.xmlfordummies.xml

fun elementTemplate(name: String) =
    """
        <$name>
        </$name>
    """.trimIndent()
