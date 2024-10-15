package webServer

import org.junit.jupiter.api.Test

private val webServer: WebServer = WebServer()

class WebServerTest {

    @Test
    fun testOne(){
        webServer.main()
    }
}