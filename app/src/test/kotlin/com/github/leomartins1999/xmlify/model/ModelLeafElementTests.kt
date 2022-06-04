package com.github.leomartins1999.xmlify.model

import com.github.leomartins1999.xmlify.exceptions.InvalidModelOperationException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ModelLeafElementTests {

    @Test
    fun `builds a ModelLeafElement from a Leaf Element`() {
        val element = element("leaf1", true)

        val model = ModelLeafElement(element, 3, -1)

        Assertions.assertEquals(element, model.element)
        Assertions.assertEquals(3, model.elementId)
        Assertions.assertEquals(-1, model.parentElementId)
    }

    @Test
    fun `updates the name of a Leaf Element and notifies observers`() {
        val observer = object : ElementObserver {
            var notified = false
            override fun onUpdateName(newName: String) {
                notified = true
            }
        }

        val element = element("oldName")
        val model = ModelLeafElement(element, 1, -1)
        model.subscribe(observer)

        model.updateName("newName")

        Assertions.assertEquals("newName", model.element.name)
        Assertions.assertTrue(observer.notified)
    }

    @Test
    fun `adds an attribute to a Leaf Element and notifies observers`() {
        val observer = object : ElementObserver {
            var notified = false
            override fun onAddAttribute(key: String, value: String) {
                notified = true
            }
        }

        val element = element("root", attributes = mapOf("a" to "b"))
        val model = ModelLeafElement(element, 1, -1)
        model.subscribe(observer)

        model.addAttribute("b", "d")

        Assertions.assertEquals(mapOf("a" to "b", "b" to "d"), model.element.attributes)
        Assertions.assertTrue(observer.notified)
    }

    @Test
    fun `adding an already existing attribute to a Leaf Element throws exception and does not notify observers`() {
        val observer = object : ElementObserver {
            var notified = false
            override fun onAddAttribute(key: String, value: String) {
                notified = true
            }
        }

        val element = element("root", attributes = mapOf("a" to "b"))
        val model = ModelLeafElement(element, 1, -1)
        model.subscribe(observer)

        assertThrows<InvalidModelOperationException> {
            model.addAttribute("a", "c")
        }

        Assertions.assertEquals(mapOf("a" to "b"), model.element.attributes)
        Assertions.assertFalse(observer.notified)
    }

    @Test
    fun `updates an attribute of a Leaf Element and notifies observers`() {
        val observer = object : ElementObserver {
            var notified = false
            override fun onUpdateAttribute(key: String, value: String) {
                notified = true
            }
        }

        val element = element("root", attributes = mapOf("a" to "b"))
        val model = ModelLeafElement(element, 1, -1)
        model.subscribe(observer)

        model.updateAttribute("a", "c")

        Assertions.assertEquals(mapOf("a" to "c"), model.element.attributes)
        Assertions.assertTrue(observer.notified)
    }

    @Test
    fun `updating a non existing attribute of a Leaf Element throws exception and does not notify observers`() {
        val observer = object : ElementObserver {
            var notified = false
            override fun onUpdateAttribute(key: String, value: String) {
                notified = true
            }
        }

        val element = element("root", attributes = mapOf("a" to "b"))
        val model = ModelLeafElement(element, 1, -1)
        model.subscribe(observer)

        assertThrows<InvalidModelOperationException> {
            model.updateAttribute("b", "d")
        }

        Assertions.assertEquals(mapOf("a" to "b"), model.element.attributes)
        Assertions.assertFalse(observer.notified)
    }

    @Test
    fun `deletes an attribute from a Leaf Element and notifies observers`() {
        val observer = object : ElementObserver {
            var notified = false
            override fun onDeleteAttribute(key: String) {
                notified = true
            }
        }

        val element = element("root", attributes = mapOf("a" to "b"))
        val model = ModelLeafElement(element, 1, -1)
        model.subscribe(observer)

        model.deleteAttribute("a")

        Assertions.assertEquals(emptyMap<String, String>(), model.element.attributes)
        Assertions.assertTrue(observer.notified)
    }

    @Test
    fun `deleting a non existing attribute from a Leaf Element throws exception and does not notify observers`() {
        val observer = object : ElementObserver {
            var notified = false
            override fun onDeleteAttribute(key: String) {
                notified = true
            }
        }

        val element = element("root", attributes = mapOf("a" to "b"))
        val model = ModelLeafElement(element, 1, -1)
        model.subscribe(observer)

        assertThrows<InvalidModelOperationException> {
            model.deleteAttribute("b")
        }

        Assertions.assertEquals(mapOf("a" to "b"), model.element.attributes)
        Assertions.assertFalse(observer.notified)
    }
}
