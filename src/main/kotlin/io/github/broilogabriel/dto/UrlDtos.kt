package io.github.broilogabriel.dto

import kotlinx.serialization.Serializable

@Serializable
data class ShortenUrlRequest(
    val url: String
)

@Serializable
data class ShortenUrlResponse(
    val shortUrl: String,
    val originalUrl: String,
    val shortCode: String
)

@Serializable
data class UrlInfoResponse(
    val originalUrl: String,
    val shortCode: String,
    val createdAt: String
)

@Serializable
data class ErrorResponse(
    val error: String,
    val message: String,
    val status: Int
)
