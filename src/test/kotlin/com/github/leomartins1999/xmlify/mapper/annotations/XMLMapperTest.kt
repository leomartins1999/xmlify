package com.github.leomartins1999.xmlify.mapper.annotations

import com.github.leomartins1999.xmlify.mapper.strategies.MappingStrategy
import com.github.leomartins1999.xmlify.mapper.xmlify
import com.github.leomartins1999.xmlify.model.Element
import com.github.leomartins1999.xmlify.model.LeafElement
import com.github.leomartins1999.xmlify.model.TreeElement
import com.github.leomartins1999.xmlify.model.element
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class XMLMapperTest {

    /**
     * Classes had to be declared outside functions as
     * declaring them inside functions led to the following
     * exception to be thrown
     * java.lang.reflect.GenericSignatureFormatError: Signature Parse error: expected '<' or ';' but got
     Remaining input:  annotated with XMLMapper use the mapper specified to map the object to an element$PersonStrategy;
     * Something wrong with kotlin reflection for this case?
     */

    @XMLMapper(PersonStrategy::class)
    data class Person(val name: String, val age: Int)

    class PersonStrategy : MappingStrategy {
        override fun toElement(instance: Any) = element("person")
    }

    @Test
    fun `classes annotated with XMLMapper use the mapper specified to map the object to an element`() {
        val person = Person("John", 23)

        val result = xmlify { person }

        assertEquals(element("person"), result)
    }

    data class Car(
        @XMLMapper(DriverStrategy::class)
        val driverName: String
    )

    class DriverStrategy : MappingStrategy {
        override fun toElement(instance: Any) = element("someDriver")
    }

    @Test
    fun `properties annotated with XMLMapper use the mapper specified to map the property to an element`() {
        val car = Car("John")

        val result = xmlify { car }
        assertEquals(TreeElement::class, result::class)

        result as TreeElement

        assertEquals(element("driverName"), result.children.first())
    }

    data class PersonWithAddress(
        val name: String,
        @XMLMapper(AddressStrategy::class)
        val address: Address
    )

    data class Address(
        val street: String,
        val zipCode: String
    )

    class AddressStrategy : MappingStrategy {
        override fun toElement(instance: Any): Element {
            instance as Address
            return element("address", "${instance.street} - ${instance.zipCode}")
        }
    }

    @Test
    fun `when annotating a class property, the mapping strategy will only be used for the property`() {
        val address = Address("some street", "12345")

        val result = xmlify { address }
        assertEquals(TreeElement::class, result::class)

        result as TreeElement
        assertTrue(result.children.contains(LeafElement("street", "some street")))
        assertTrue(result.children.contains(LeafElement("zipCode", "12345")))
    }

    @Test
    fun `XMLMapper can be used to specify which property will be used as the element content`() {
        val address = Address("some street", "12345")
        val personWithAddress = PersonWithAddress("John", address)

        val result = xmlify { personWithAddress }
        assertEquals(TreeElement::class, result::class)

        result as TreeElement
        assertTrue(result.children.contains(LeafElement("name", "John")))
        assertTrue(result.children.contains(LeafElement("address", "${address.street} - ${address.zipCode}")))
    }
}
