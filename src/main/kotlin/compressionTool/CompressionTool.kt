package compressionTool

const val fileName = "miserables.txt"
class CompressionTool {
    fun main() {
        val characterOccurrences = countCharacterOccurrence(fileName)

        val rootNode = buildNodeTree(characterOccurrences).poll()

        val equivalenceTable = generateEquivalenceTable(rootNode)

        encodeFile(fileName, "miserablesEncoded.txt", equivalenceTable, characterOccurrences)

        decodeFile("miserablesEncoded.txt", "miserablesDecoded.txt")
    }

}