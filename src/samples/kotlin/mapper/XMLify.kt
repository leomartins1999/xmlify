package mapper

import com.github.leomartins1999.xmlify.mapper.xmlify

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
    val element = xmlify { player }
}
