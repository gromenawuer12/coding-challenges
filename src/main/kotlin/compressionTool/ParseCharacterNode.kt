package compressionTool

fun parseCharacterNode(input: List<String>): List<CharacterNode> {
    val characterOccurrences = mutableListOf<CharacterNode>()

    for (i in 0 until input.size - 1 step 2) {
        val character = input[i].single()
        val weight = input[i + 1].toInt()
        characterOccurrences.add(CharacterNode(character, weight))
    }

    return characterOccurrences
}