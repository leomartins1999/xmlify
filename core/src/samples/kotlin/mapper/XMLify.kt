package mapper

import com.github.leomartins1999.xmlify.mapper.xmlify

enum class Numbers { ONE, TWO, THREE }

fun xmlifyExample() {
    /**
     * Maps an object to a Tree Element
     */
    data class SoccerPlayer(
        val name: String,
        val number: Int,
        val retired: Boolean
    )

    val player = SoccerPlayer("Bernardo Silva", 20, false)
    val objectElement = xmlify { player }

    /**
     * Maps a value to a Leaf Element
     */
    val valueElement = xmlify { "Flower" }

    /**
     * Maps a collection to a TreeElement
     */
    val collectionElement = xmlify { listOf(1, 2, 3, 4, 5) }

    /**
     * Maps an enum to a Leaf Element
     */
    val enumElement = xmlify { Numbers.ONE }
}
