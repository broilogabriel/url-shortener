package io.github.broilogabriel.database

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.ktorm.database.Database

object DatabaseFactory {
    lateinit var database: Database
        private set

    fun init() {
        val dataSource = createHikariDataSource()
        database = Database.connect(dataSource)

        // TODO move it to a migration tool like Flyway or Liquibase
        // Create table if not exists
        database.useConnection { conn ->
            conn.createStatement().execute(
                """
                CREATE TABLE IF NOT EXISTS urls (
                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                    short_code VARCHAR(10) UNIQUE NOT NULL,
                    original_url VARCHAR(2048) NOT NULL,
                    created_at VARCHAR(50) NOT NULL
                )
            """.trimIndent()
            )
        }
    }

    private fun createHikariDataSource(): HikariDataSource {
        val config = HikariConfig().apply {
            driverClassName = "org.h2.Driver"
            jdbcUrl = "jdbc:h2:mem:urlshortener;DB_CLOSE_DELAY=-1"
            maximumPoolSize = 3
            isAutoCommit = false
            transactionIsolation = "TRANSACTION_REPEATABLE_READ"
            validate()
        }
        return HikariDataSource(config)
    }
}