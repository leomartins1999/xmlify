package mapper

import com.github.leomartins1999.xmlify.mapper.annotations.XMLAttribute
import com.github.leomartins1999.xmlify.mapper.annotations.XMLIgnore
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
    @XMLIgnore
    val secret: String
)

/**
 * Attributes can be defined for both
 * classes and class properties
 * Multiple attributes can be defined
 */
@XMLAttribute("brand", "BMW")
@XMLAttribute("model", "X1")
data class Car(
    @XMLAttribute("originCountry", "Germany")
    val factoryNumber: String
)
