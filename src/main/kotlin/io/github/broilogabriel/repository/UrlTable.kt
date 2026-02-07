package io.github.broilogabriel.repository

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.long
import org.ktorm.schema.varchar

interface Url : Entity<Url> {
    val id: Long
    val shortCode: String
    val originalUrl: String
    val createdAt: String
}

object UrlTable : Table<Url>("urls") {
    val id = long("id").primaryKey().bindTo { it.id }
    val shortCode = varchar("short_code").bindTo { it.shortCode }
    val originalUrl = varchar("original_url").bindTo { it.originalUrl }
    val createdAt = varchar("created_at").bindTo { it.createdAt }
}