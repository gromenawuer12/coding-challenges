package compressionTool

import exception.CustomException
import java.io.File

const val resourceDirectory = "src/main/resources"
fun encodeFile(
    inputFileName: String,
    outputFileName: String,
    equivalenceTable: Map<Char, String>,
    characterOccurrences: List<CharacterNode>
) {
    val resource = object {}.javaClass.getResource("/$inputFileName")
        ?: throw IllegalArgumentException("Resource does not exist: $inputFileName")
    val content = File(resource.toURI())
    val translatedContent = StringBuilder()
    val body = StringBuilder()

    content.bufferedReader().use { reader ->
        var charCode: Int
        while (reader.read().also { charCode = it } != -1) {
            val code = equivalenceTable[charCode.toChar()] ?: throw CustomException("Character not Found!!")
            body.append(code)
        }
    }

    val (byteStrings, numZerosAdded) = splitIntoBytes(body.toString())
    val byteList = byteStrings.map { it.toUByte(2).toByte() }

    translatedContent.append("<$numZerosAdded>")
    translatedContent.append(characterOccurrences.joinToString("<ñññ>"))
    translatedContent.append("<END>")
    translatedContent.append(byteList.joinToString(","))

    File("$resourceDirectory/$outputFileName").writeText(translatedContent.toString())
}

private fun splitIntoBytes(bitString: String): Pair<List<String>, Int> {
    val originalLength = bitString.length
    val paddedLength = ((originalLength + 7) / 8) * 8
    val numZerosAdded = paddedLength - originalLength
    val paddedString = bitString.padStart(paddedLength, '0')

    return Pair(paddedString.chunked(8), numZerosAdded)
}