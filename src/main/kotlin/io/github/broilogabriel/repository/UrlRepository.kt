package io.github.broilogabriel.repository

import io.github.broilogabriel.service.UrlNotFoundException
import io.github.broilogabriel.util.Base62Encoder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.ktorm.database.Database
import org.ktorm.dsl.*
import java.time.LocalDateTime
import kotlin.math.absoluteValue

class UrlRepository(private val database: Database) {

    suspend fun insertUrl(originalUrl: String): String {
        return withContext(Dispatchers.IO) {
            database.useTransaction {
                // Insert with empty short code based on hash first to get an ID
                val tempCode = Base62Encoder.encode(originalUrl.hashCode().absoluteValue.toLong())
                val id = database.insertAndGenerateKey(UrlTable) {
                    set(it.shortCode, tempCode)
                    set(it.originalUrl, originalUrl)
                    set(it.createdAt, LocalDateTime.now().toString())
                } as Long
                // Generate short code from ID
                val generatedShortCode = Base62Encoder.encode(id)
                // Update with generated short code
                database.update(UrlTable) {
                    set(it.shortCode, generatedShortCode)
                    where { it.id eq id }
                }
                return@withContext generatedShortCode
            }

        }
    }

    suspend fun getUrlFromShortCode(shortCode: String): Url {
        return withContext(Dispatchers.IO) {
            database
                .from(UrlTable)
                .select()
                .where { UrlTable.shortCode eq shortCode }
                .map { row -> UrlTable.createEntity(row) }
                .firstOrNull()
                ?: throw UrlNotFoundException(shortCode)
        }
    }

}