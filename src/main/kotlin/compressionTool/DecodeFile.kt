package compressionTool

import exception.CustomException
import java.io.File

fun main() {
    decodeFile("simpleEncoded.txt", "simpleDecoded.txt")
}

fun decodeFile(
    inputFileName: String,
    outputFileName: String
) {
    val resource = object {}.javaClass.getResource("/$inputFileName")
        ?: throw IllegalArgumentException("Resource does not exist: $inputFileName")

    val content = File(resource.toURI())

    val header = StringBuilder()
    val body = StringBuilder()
    val endMarker = "<END>"
    val buffer = StringBuilder()
    var hasSeparator = false

    content.bufferedReader().use { bufferedReader ->
        var charCode: Int
        while (bufferedReader.read().also { charCode = it } != -1) {
            val char = charCode.toChar()
            buffer.append(char)

            if (buffer.endsWith(endMarker)) {
                hasSeparator = true

                buffer.setLength(buffer.length - endMarker.length)
                header.append(buffer)
                buffer.clear()
            } else if (hasSeparator) {
                body.append(char)
            }
        }
        if (!hasSeparator) throw CustomException("ERROR!!")
    }

    val regex = Regex("<(\\d+)>")
    val matchResult = regex.find(header) ?: throw CustomException("Number of Zeros Added Not Found!!")
    val numZerosAdded = matchResult.groupValues[1]
    val cleanHeader = header.replaceFirst(regex, "")

    val headerElements = cleanHeader.toString().split("<ñññ>")

    val characterOccurrences = parseCharacterNode(headerElements)
    val rootNode = buildNodeTree(characterOccurrences).poll()
    val equivalenceTable = generateEquivalenceTable(rootNode)
    val reversedTable = equivalenceTable.entries.associateBy({ it.value }, { it.key })
    val translatedContent = StringBuilder()
    val encodedText = bytesToBits(body.split(",").map { it.toByte() }.toByteArray(), numZerosAdded.toInt())

    var index = 0
    while (index < encodedText.length) {
        for (length in 1..encodedText.length - index) {
            val substring = encodedText.substring(index, index + length)

            if (reversedTable.containsKey(substring)) {
                translatedContent.append(reversedTable[substring])
                index += length
                break
            }
            if (substring.length > 100) throw CustomException("CHARACTER NOT FOUND")
        }

    }
    File("$resourceDirectory/$outputFileName").writeText(translatedContent.toString())
}

private fun bytesToBits(byteList: ByteArray, numZerosAdded: Int): String {
    val bitString = byteList.joinToString("") {
        it.toUByte().toString(2).padStart(8, '0')
    }

    return bitString.drop(numZerosAdded)
}

