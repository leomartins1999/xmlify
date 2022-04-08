package com.github.leomartins1999.xmlfordummies.xml

import com.github.leomartins1999.xmlfordummies.document
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class DocumentTests {

    @Test
    fun `creates document with default version and encoding`() {
        val document = document()

        assertEquals(XMLVersion.V1_0, document.version)
        assertEquals(Charsets.UTF_8, document.encoding)
    }

    @Test
    fun `creates document with specified version and encoding`() {
        val version = XMLVersion.V2_0
        val encoding = Charsets.ISO_8859_1

        val document = document(version = version, encoding = encoding)

        assertEquals(version, document.version)
        assertEquals(encoding, document.encoding)
    }

    @Test
    fun `renders an empty default document`() {
        val expected = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"

        val document = document()

        assertEquals(expected, document.render())
    }

    @Test
    fun `renders an empty document with specific versions and encoding`() {
        val expected = "<?xml version=\"1.1\" encoding=\"UTF-16\"?>"

        val document = document(version = XMLVersion.V1_1, encoding = Charsets.UTF_16)

        assertEquals(expected, document.render())
    }

    @Test
    fun `renders a document with an element`() {
        val elements = listOf(Element("myElement"))

        val expected = """
            <?xml version="1.0" encoding="UTF-8"?>
            <myElement/>
        """.trimIndent()

        val document = document(elements = elements)

        assertEquals(expected, document.render())
    }

    @Test
    fun `renders a document with multiple elements`() {
        val elements = listOf(
            Element("myElement1"),
            Element("myElement2"),
            Element("myElement3")
        )

        val expected = """
            <?xml version="1.0" encoding="UTF-8"?>
            <myElement1/>
            <myElement2/>
            <myElement3/>
        """.trimIndent()

        val document = document(elements = elements)

        assertEquals(expected, document.render())
    }
}
