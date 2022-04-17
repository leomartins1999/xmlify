import com.github.leomartins1999.xmlify.model.XMLVersion
import com.github.leomartins1999.xmlify.model.document
import com.github.leomartins1999.xmlify.model.element

fun documentExample() {

    /**
     * Creates an XML Document
     */
    val root = element("root")
    val doc = document(root)

    /**
     * Version and encoding are configurable
     */
    val docV2 = document(root, XMLVersion.V2_0, Charsets.ISO_8859_1)

    /**
     * Renders an XML Document
     */
    println(doc.render())
}
