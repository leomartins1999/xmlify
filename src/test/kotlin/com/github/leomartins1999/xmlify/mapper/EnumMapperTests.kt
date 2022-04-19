package com.github.leomartins1999.xmlify.mapper

import com.github.leomartins1999.xmlify.model.LeafElement
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class EnumMapperTests {

    enum class Direction {
        NORTH, SOUTH, WEST, EAST
    }

    @Test
    fun `maps an enum by it's name`() {
        val dir = Direction.NORTH

        val element = xmlify { dir }

        assertEquals(LeafElement("Direction", "NORTH"), element)
    }
}
