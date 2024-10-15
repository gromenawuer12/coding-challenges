package compressionTool

class CharacterNode(
    private val character: Char,
    private val weight: Int
) : Node {
    override fun weight(): Int {
        return weight
    }

    fun character(): Char {
        return character
    }

    override fun toString(): String {
        return "${character}<ñññ>${weight}"
    }

}
