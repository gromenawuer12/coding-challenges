package wcTool

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import java.nio.charset.StandardCharsets

class WcToolTest {

    private val wcToolTest: WcTool = WcTool()

    @Test
    fun testNumberOfBytes() {
        val input = "ccwc -c test.txt\n"

        val inputStream = ByteArrayInputStream(input.toByteArray(StandardCharsets.UTF_8))
        System.setIn(inputStream)

        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        wcToolTest.main()

        val expectedOutput = "342190 test.txt\n"
        assertEquals(expectedOutput.trim(), outputStream.toString().trim())

        System.setIn(System.`in`)
        System.setOut(System.out)
    }

    @Test
    fun testNumberOfLines() {
        val input = "ccwc -l test.txt\n"

        val inputStream = ByteArrayInputStream(input.toByteArray(StandardCharsets.UTF_8))
        System.setIn(inputStream)

        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        wcToolTest.main()

        val expectedOutput = "7145 test.txt\n"
        assertEquals(expectedOutput.trim(), outputStream.toString().trim())

        System.setIn(System.`in`)
        System.setOut(System.out)
    }

    @Test
    fun testNumberOfWords() {
        val input = "ccwc -w test.txt\n"

        val inputStream = ByteArrayInputStream(input.toByteArray(StandardCharsets.UTF_8))
        System.setIn(inputStream)

        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        wcToolTest.main()

        val expectedOutput = "58164 test.txt\n"
        assertEquals(expectedOutput.trim(), outputStream.toString().trim())

        System.setIn(System.`in`)
        System.setOut(System.out)
    }

    @Test
    fun testNumberOfCharacters() {
        val input = "ccwc -m test.txt\n"

        val inputStream = ByteArrayInputStream(input.toByteArray(StandardCharsets.UTF_8))
        System.setIn(inputStream)

        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        wcToolTest.main()

        val expectedOutput = "339292 test.txt\n"
        assertEquals(expectedOutput.trim(), outputStream.toString().trim())

        System.setIn(System.`in`)
        System.setOut(System.out)
    }

    @Test
    fun testNumberOfBytesWordsLines() {
        val input = "ccwc test.txt\n"

        val inputStream = ByteArrayInputStream(input.toByteArray(StandardCharsets.UTF_8))
        System.setIn(inputStream)

        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        wcToolTest.main()

        val expectedOutput = "7145 58164 342190 test.txt\n"
        assertEquals(expectedOutput.trim(), outputStream.toString().trim())

        System.setIn(System.`in`)
        System.setOut(System.out)
    }

    @Test
    fun testNumberOfLinesWithType() {
        val input = "type test.txt | ccwc -l\n"

        val inputStream = ByteArrayInputStream(input.toByteArray(StandardCharsets.UTF_8))
        System.setIn(inputStream)

        val outputStream = ByteArrayOutputStream()
        System.setOut(PrintStream(outputStream))

        wcToolTest.main()

        val expectedOutput = "7145 test.txt\n"
        assertEquals(expectedOutput.trim(), outputStream.toString().trim())

        System.setIn(System.`in`)
        System.setOut(System.out)
    }
}