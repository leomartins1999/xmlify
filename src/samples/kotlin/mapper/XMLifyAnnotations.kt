package mapper

import com.github.leomartins1999.xmlify.mapper.annotations.XMLAttribute
import com.github.leomartins1999.xmlify.mapper.annotations.XMLIgnore
import com.github.leomartins1999.xmlify.mapper.annotations.XMLMapper
import com.github.leomartins1999.xmlify.mapper.annotations.XMLName
import com.github.leomartins1999.xmlify.mapper.strategies.MappingStrategy
import com.github.leomartins1999.xmlify.model.Element
import com.github.leomartins1999.xmlify.model.element

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

/**
 * XMLMapper can be used to define a custom
 * mapper for a given class or class property
 */
@XMLMapper(NodeStrategy::class)
data class Node(
    val value: Int,
    val next: Node? = null
)

/**
 * Mapping strategies must:
 * - implement the MappingStrategy interface
 * - Have a no arguments constructor
 */
class NodeStrategy : MappingStrategy {
    override fun toElement(instance: Any): Element {
        instance as Node
        return element("n", instance.value)
    }
}
