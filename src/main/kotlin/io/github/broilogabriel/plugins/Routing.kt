package io.github.broilogabriel.plugins

import io.github.broilogabriel.routes.urlRoutes
import io.github.broilogabriel.service.UrlService
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting(urlService: UrlService) {
    routing {
        urlRoutes(urlService)
    }
}
