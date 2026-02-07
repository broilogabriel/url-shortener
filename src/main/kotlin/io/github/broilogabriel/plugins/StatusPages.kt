package io.github.broilogabriel.plugins

import io.github.broilogabriel.dto.ErrorResponse
import io.github.broilogabriel.service.InvalidUrlException
import io.github.broilogabriel.service.UrlNotFoundException
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*

fun Application.configureStatusPages() {
    install(StatusPages) {
        exception<UrlNotFoundException> { call, cause ->
            call.respond(
                HttpStatusCode.NotFound,
                ErrorResponse(
                    error = "Not Found",
                    message = cause.message ?: "URL not found",
                    status = HttpStatusCode.NotFound.value
                )
            )
        }

        exception<InvalidUrlException> { call, cause ->
            call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse(
                    error = "Bad Request",
                    message = cause.message ?: "Invalid URL",
                    status = HttpStatusCode.BadRequest.value
                )
            )
        }

        exception<IllegalArgumentException> { call, cause ->
            call.respond(
                HttpStatusCode.BadRequest,
                ErrorResponse(
                    error = "Bad Request",
                    message = cause.message ?: "Invalid request",
                    status = HttpStatusCode.BadRequest.value
                )
            )
        }

        exception<Throwable> { call, cause ->
            call.respond(
                HttpStatusCode.InternalServerError,
                ErrorResponse(
                    error = "Internal Server Error",
                    message = cause.message ?: "An unexpected error occurred",
                    status = HttpStatusCode.InternalServerError.value
                )
            )
        }
    }
}
