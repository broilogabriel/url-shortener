package io.github.broilogabriel

import io.github.broilogabriel.database.DatabaseFactory
import io.github.broilogabriel.plugins.configureRouting
import io.github.broilogabriel.plugins.configureSerialization
import io.github.broilogabriel.plugins.configureStatusPages
import io.github.broilogabriel.repository.UrlRepository
import io.github.broilogabriel.service.UrlService
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    DatabaseFactory.init()
    // fixme : baseUrl should be read from configuration file or environment variable
    val baseUrl = "http://localhost:8080"
    val urlRepository = UrlRepository(DatabaseFactory.database)
    val urlService = UrlService(urlRepository, baseUrl)
    configureSerialization()
    configureStatusPages()
    configureRouting(urlService)
}
