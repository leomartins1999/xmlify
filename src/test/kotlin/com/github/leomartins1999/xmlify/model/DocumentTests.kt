package com.github.leomartins1999.xmlify.model

import com.github.leomartins1999.xmlify.document
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DocumentTests {

    @Test
    fun `creates document with default version and encoding`() {
        val document = document(LeafElement("myElement"))

        assertEquals(XMLVersion.V1_0, document.version)
        assertEquals(Charsets.UTF_8, document.encoding)
    }

    @Test
    fun `creates document with specified version and encoding`() {
        val version = XMLVersion.V2_0
        val encoding = Charsets.ISO_8859_1

        val document = document(LeafElement("myElement"), version, encoding)

        assertEquals(version, document.version)
        assertEquals(encoding, document.encoding)
    }

    @Test
    fun `renders a document with default version and encoding`() {
        val element = LeafElement("myElement")

        val expected = """
            <?xml version="1.0" encoding="UTF-8"?>
            <myElement/>
        """.trimIndent()

        val document = document(element)

        assertEquals(expected, document.render())
    }

    @Test
    fun `renders a document with the specified version and encoding`() {
        val element = LeafElement("myElement")

        val expected = """
            <?xml version="1.1" encoding="UTF-16"?>
            <myElement/>
        """.trimIndent()

        val document = document(element, XMLVersion.V1_1, Charsets.UTF_16)

        assertEquals(expected, document.render())
    }
}
