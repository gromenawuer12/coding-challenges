package wcTool

import java.io.File
import java.nio.file.Paths
import exception.CustomException

class WcTool {
    fun main() {
        val input = readLine()
        val inputRegex = Regex("(type .+\\..+ \\| ccwc( -l| -c| -w| -m)?)|(ccwc( -l| -c| -w| -m)? .+\\..+)")

        if (input?.matches(inputRegex) == false) throw CustomException("Invalid command!! $input")

        val arguments = input!!.split(" ")

        println(countFromCommandLineOrFile(arguments))
    }

    private fun countFromCommandLineOrFile(arguments: List<String>): String {
        var result = ""

        val fileName = arguments.find { it.matches(Regex(".+\\..+")) }
        val resource = object {}.javaClass.getResource("/$fileName")
            ?: throw IllegalArgumentException("Resource does not exist: $fileName")

        val output = if (arguments[0] == "type") {
            ProcessBuilder(
                "cmd",
                "/c",
                "type",
                Paths.get(resource.toURI()).toString()
            ).start().inputStream.bufferedReader().use { it.readText() }
        } else {
            File(resource.toURI()).readText()
        }

        if (arguments.contains("-l")) result += countLines(output)
        if (arguments.contains("-w")) result += countWords(output)
        if (arguments.contains("-c")) result += countBytes(output)
        if (arguments.contains("-m")) result += countCharacters(output)

        if (result == "") result += "${countLines(output)} ${countWords(output)} ${countBytes(output)}"

        return "$result $fileName"
    }

    private fun countBytes(input: String): Int {
        return input.toByteArray().size
    }


    private fun countLines(input: String): Int {
        return input.lines().size - 1
    }


    private fun countWords(input: String): Int {
        return input.trim().split("\\s+".toRegex()).size
    }


    private fun countCharacters(input: String): Int {
        return input.length
    }
}