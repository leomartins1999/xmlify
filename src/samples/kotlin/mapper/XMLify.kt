package mapper

import com.github.leomartins1999.xmlify.mapper.xmlify

fun xmlifyExample() {
    /**
     * Maps an object to an Element (Tree Element in this case)
     */
    data class SoccerPlayer(
        val name: String,
        val number: Int,
        val retired: Boolean
    )
    val player = SoccerPlayer("Bernardo Silva", 20, false)
    val objectElement = xmlify { player }

    /**
     * Maps a value to an Element (Leaf Element in this case)
     */
    val valueElement = xmlify { "Flower" }
}
