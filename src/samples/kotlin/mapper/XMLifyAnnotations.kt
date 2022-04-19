package mapper

import com.github.leomartins1999.xmlify.mapper.annotations.XMLName

/**
 * Element names (for classes and properties)
 * can be configured via annotations
 */
@XMLName("book")
data class Entity(
    @XMLName("id") val isbn: String
)

/**
 * Class properties can also be hidden
 */
data class Client(
    val id: String,
    val secret: String
)
