package compressionTool

import java.io.File

fun countCharacterOccurrence (fileName: String): List<CharacterNode>{
    val resource = object {}.javaClass.getResource("/$fileName")
        ?: throw IllegalArgumentException("Resource does not exist: $fileName")
    val content = File(resource.toURI())

    val characterCountMap = mutableMapOf<Char, Int>()

    content.bufferedReader().use { reader ->
        var charCode: Int
        while (reader.read().also { charCode = it } != -1) {
            val char = charCode.toChar()
            characterCountMap[char] = characterCountMap.getOrDefault(char, 0) + 1
        }
    }

    return characterCountMap
        .map { (character, frequency) -> CharacterNode(character, frequency) }
        .sortedBy { it.weight() }
}