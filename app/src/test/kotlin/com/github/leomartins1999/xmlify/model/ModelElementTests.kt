package com.github.leomartins1999.xmlify.model

import com.github.leomartins1999.xmlify.exceptions.InvalidModelOperationException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ModelElementTests {

    @Nested
    inner class ModelLeafElementTests {
        @Test
        fun `builds a ModelLeafElement from a Leaf Element`() {
            val element = element("leaf1", true)

            val model = ModelLeafElement(element, 3, -1)

            assertEquals(element, model.element)
            assertEquals(3, model.elementId)
            assertEquals(-1, model.previousElementId)
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

            assertEquals("newName", model.element.name)
            assertTrue(observer.notified)
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

            assertEquals(mapOf("a" to "b", "b" to "d"), model.element.attributes)
            assertTrue(observer.notified)
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

            assertEquals(mapOf("a" to "b"), model.element.attributes)
            assertFalse(observer.notified)
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

            assertEquals(mapOf("a" to "c"), model.element.attributes)
            assertTrue(observer.notified)
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

            assertEquals(mapOf("a" to "b"), model.element.attributes)
            assertFalse(observer.notified)
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

            assertEquals(emptyMap<String, String>(), model.element.attributes)
            assertTrue(observer.notified)
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

            assertEquals(mapOf("a" to "b"), model.element.attributes)
            assertFalse(observer.notified)
        }
    }

    @Nested
    inner class ModelTreeElementTests {
        @Test
        fun `builds a ModelTreeElement from a Tree Element`() {
            val element = element("root", children = emptyList())

            val model = ModelTreeElement(element, 3, -1)

            assertEquals(element, model.element)
            assertEquals(3, model.elementId)
            assertEquals(-1, model.previousElementId)
        }

        @Test
        fun `updates the name of a Tree Element and notifies observers`() {
            val observer = object : ElementObserver {
                var notified = false
                override fun onUpdateName(newName: String) {
                    notified = true
                }
            }

            val element = element("oldName", emptyList())
            val model = ModelTreeElement(element, 1, -1)
            model.subscribe(observer)

            model.updateName("newName")

            assertEquals("newName", model.element.name)
            assertTrue(observer.notified)
        }

        @Test
        fun `adds an attribute to a Tree Element and notifies observers`() {
            val observer = object : ElementObserver {
                var notified = false
                override fun onAddAttribute(key: String, value: String) {
                    notified = true
                }
            }

            val element = element("root", emptyList(), attributes = mapOf("a" to "b"))
            val model = ModelTreeElement(element, 1, -1)
            model.subscribe(observer)

            model.addAttribute("b", "d")

            assertEquals(mapOf("a" to "b", "b" to "d"), model.element.attributes)
            assertTrue(observer.notified)
        }

        @Test
        fun `adding an already existing attribute to a Tree Element throws exception and does not notify observers`() {
            val observer = object : ElementObserver {
                var notified = false
                override fun onAddAttribute(key: String, value: String) {
                    notified = true
                }
            }

            val element = element("root", emptyList(),  attributes = mapOf("a" to "b"))
            val model = ModelTreeElement(element, 1, -1)
            model.subscribe(observer)

            assertThrows<InvalidModelOperationException> {
                model.addAttribute("a", "c")
            }

            assertEquals(mapOf("a" to "b"), model.element.attributes)
            assertFalse(observer.notified)
        }

        @Test
        fun `updates an attribute of a Tree Element and notifies observers`() {
            val observer = object : ElementObserver {
                var notified = false
                override fun onUpdateAttribute(key: String, value: String) {
                    notified = true
                }
            }

            val element = element("root", emptyList() ,attributes = mapOf("a" to "b"))
            val model = ModelTreeElement(element, 1, -1)
            model.subscribe(observer)

            model.updateAttribute("a", "c")

            assertEquals(mapOf("a" to "c"), model.element.attributes)
            assertTrue(observer.notified)
        }

        @Test
        fun `updating a non existing attribute of a Tree Element throws exception and does not notify observers`() {
            val observer = object : ElementObserver {
                var notified = false
                override fun onUpdateAttribute(key: String, value: String) {
                    notified = true
                }
            }

            val element = element("root", emptyList(), attributes = mapOf("a" to "b"))
            val model = ModelTreeElement(element, 1, -1)
            model.subscribe(observer)

            assertThrows<InvalidModelOperationException> {
                model.updateAttribute("b", "d")
            }

            assertEquals(mapOf("a" to "b"), model.element.attributes)
            assertFalse(observer.notified)
        }

        @Test
        fun `deletes an attribute from a Tree Element and notifies observers`() {
            val observer = object : ElementObserver {
                var notified = false
                override fun onDeleteAttribute(key: String) {
                    notified = true
                }
            }

            val element = element("root", emptyList(),  attributes = mapOf("a" to "b"))
            val model = ModelTreeElement(element, 1, -1)
            model.subscribe(observer)

            model.deleteAttribute("a")

            assertEquals(emptyMap<String, String>(), model.element.attributes)
            assertTrue(observer.notified)
        }

        @Test
        fun `deleting a non existing attribute from a Tree Element throws exception and does not notify observers`() {
            val observer = object : ElementObserver {
                var notified = false
                override fun onDeleteAttribute(key: String) {
                    notified = true
                }
            }

            val element = element("root", emptyList(), attributes = mapOf("a" to "b"))
            val model = ModelTreeElement(element, 1, -1)
            model.subscribe(observer)

            assertThrows<InvalidModelOperationException> {
                model.deleteAttribute("b")
            }

            assertEquals(mapOf("a" to "b"), model.element.attributes)
            assertFalse(observer.notified)
        }
    }
}
