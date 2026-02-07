package io.github.broilogabriel.service

import io.github.broilogabriel.dto.ShortenUrlResponse
import io.github.broilogabriel.dto.UrlInfoResponse
import io.github.broilogabriel.repository.UrlRepository
import java.net.URI

class UrlService(private val urlRepository: UrlRepository, private val baseUrl: String) {

    suspend fun shortenUrl(originalUrl: String): ShortenUrlResponse {
        validateUrl(originalUrl)
        val shortCode = urlRepository.insertUrl(originalUrl)
        return ShortenUrlResponse(
            shortUrl = "$baseUrl/$shortCode",
            originalUrl = originalUrl,
            shortCode = shortCode
        )
    }

    suspend fun getOriginalUrl(shortCode: String): String {
        return urlRepository.getUrlFromShortCode(shortCode).originalUrl
    }

    suspend fun getUrlInfo(shortCode: String): UrlInfoResponse {
        val url = urlRepository.getUrlFromShortCode(shortCode)
        return UrlInfoResponse(
            originalUrl = url.originalUrl,
            shortCode = url.shortCode,
            createdAt = url.createdAt
        )
    }

    private fun validateUrl(url: String) {
        if (url.isBlank()) {
            throw InvalidUrlException("URL cannot be blank")
        }

        try {
            val uri = URI(url)
            if (uri.scheme == null || uri.host == null) {
                throw InvalidUrlException("Invalid URL format: must include scheme and host")
            }
            if (uri.scheme !in listOf("http", "https")) {
                throw InvalidUrlException("URL must use HTTP or HTTPS protocol")
            }
        } catch (e: Exception) {
            if (e is InvalidUrlException) throw e
            throw InvalidUrlException("Invalid URL format: ${e.message}")
        }
    }
}

class UrlNotFoundException(shortCode: String) : Exception("URL not found for short code: $shortCode")
class InvalidUrlException(message: String) : Exception(message)
