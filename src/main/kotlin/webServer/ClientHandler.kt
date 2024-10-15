package webServer

import java.io.File
import java.net.Socket
import java.net.URLDecoder
import java.nio.charset.Charset
import java.nio.charset.StandardCharsets

const val resources = "src/main/resources/www"

class ClientHandler(private val client: Socket) : Runnable {
    override fun run() {
        main()
    }

    fun main() {
        client.getInputStream().bufferedReader().use { reader ->
            val requestLine = reader.readLine()

            val requestedFile = URLDecoder.decode(requestLine.split(" ")[1], StandardCharsets.UTF_8)

            val baseDir = File(resources).canonicalFile

            val file = if (requestedFile == "/") {
                File(baseDir, "index.html").canonicalFile
            } else {
                File(baseDir, requestedFile).canonicalFile
            }

            var httpResponse = """
                HTTP/1.1 404 Not Found
                Content-Type: text/plain
                Content-Length: 13

                404 Not Found
                """.trimIndent()

            if (file.exists() && !file.isDirectory && file.startsWith(baseDir)) {
                val content = file.readText()
                val okResponse = StringBuilder()
                okResponse.append("HTTP/1.1 200 OK\r\n")
                okResponse.append("Content-Type: text/html; charset=UTF-8\r\n")
                okResponse.append("Content-Length: ${content.length}\r\n")
                okResponse.append("\r\n")
                okResponse.append(content)

                httpResponse = okResponse.toString()
            }
            client.getOutputStream().use { output ->
                output.write(httpResponse.toByteArray())
            }

        }
        client.close()
    }
}
