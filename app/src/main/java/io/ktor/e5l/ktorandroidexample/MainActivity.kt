package io.ktor.e5l.ktorandroidexample

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import io.ktor.application.call
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.cio.CIO
import io.ktor.server.engine.embeddedServer
import kotlinx.coroutines.experimental.DefaultDispatcher
import kotlinx.coroutines.experimental.runBlocking

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startServer()
        val response = makeRequest()

        Toast.makeText(this, response, Toast.LENGTH_LONG).show()
    }

    private fun startServer() {
        val server = embeddedServer(CIO, port = 8080) {
            routing {
                get("/") {
                    call.respondText("Hello World!", ContentType.Text.Plain)
                }
                get("/demo") {
                    call.respondText("HELLO WORLD!")
                }
            }
        }
        server.start()
    }

    private fun makeRequest(): String = runBlocking(DefaultDispatcher) {
        HttpClient(io.ktor.client.engine.cio.CIO).use { client ->
            return@runBlocking client.get<String>(port = 8080, path = "/demo")
        }
    }
}
