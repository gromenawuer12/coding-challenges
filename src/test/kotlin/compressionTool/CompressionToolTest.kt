package compressionTool

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.io.File
import java.net.URI

const val fileName = "simple.txt"
class CompressionToolTest {
    private val compressionToolTest: CompressionTool = CompressionTool()

    @Test
    fun testOne() {
        val result = "NumericNode[weight:306,elements:[e120,NumericNode[weight:186,elements:[NumericNode[weight:79,elements:[u37,d42]],NumericNode[weight:107,elements:[l42,NumericNode[weight:65,elements:[c32,NumericNode[weight:33,elements:[NumericNode[weight:9,elements:[z2,k7]],m24]]]]]]]]]]"
        val rootNode = buildNodeTree(countCharacterOccurrence(fileName)).poll()

        Assertions.assertEquals(result, rootNode.toString())
    }

    @Test
    fun testTwo() {
        val result = mapOf<Char, String>(
            'e' to "0",
            'u' to "100",
            'd' to "101",
            'l' to "110",
            'c' to "1110",
            'z' to "111100",
            'k' to "111101",
            'm' to "11111"
        )
        val equivalenceTable = generateEquivalenceTable(buildNodeTree(countCharacterOccurrence(fileName)).poll())

        Assertions.assertEquals(result, equivalenceTable)
    }

    @Test
    fun testThree() {
        compressionToolTest.main()

        val file1Path = object {}.javaClass.getResource("/simple.txt")
            ?: throw IllegalArgumentException("Resource does not exist: /miserables.txt")
        val file2Path = object {}.javaClass.getResource("/simpleDecoded.txt")
            ?: throw IllegalArgumentException("Resource does not exist: /miserables.txt")
        assertTrue(compareFilesLineByLine(file1Path.toURI(), file2Path.toURI()), "Files should be equal")
    }

    private fun compareFilesLineByLine(file1Path: URI, file2Path: URI): Boolean {
        val file1 = File(file1Path)
        val file2 = File(file2Path)

        return file1.readText() == file2.readText()
    }
}