package webServer

import java.net.ServerSocket

class WebServer {
    fun main(){
        val server = ServerSocket(8080)
        println("Servidor escuchando en el puerto 8080...")

        while (true) {
            val client = server.accept()
            println("Cliente conectado")
            val clientHandler = ClientHandler(client)
            Thread(clientHandler).start()
        }
    }
}