package io.github.broilogabriel.routes

import io.github.broilogabriel.dto.ShortenUrlRequest
import io.github.broilogabriel.service.UrlService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.urlRoutes(urlService: UrlService) {

    route("/api") {
        post("/shorten") {
            val request = call.receive<ShortenUrlRequest>()
            val response = urlService.shortenUrl(request.url)
            call.respond(HttpStatusCode.Created, response)
        }

        get("/url/{shortCode}") {
            val shortCode = call.parameters["shortCode"]
                ?: throw IllegalArgumentException("Short code is required")
            val urlInfo = urlService.getUrlInfo(shortCode)
            call.respond(urlInfo)
        }
    }

    get("/{shortCode}") {
        val shortCode = call.parameters["shortCode"]
            ?: throw IllegalArgumentException("Short code is required")
        val originalUrl = urlService.getOriginalUrl(shortCode)
        call.respondRedirect(originalUrl, permanent = false)
    }
}
