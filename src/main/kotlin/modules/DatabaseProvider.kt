@file:Suppress("InvalidPackageDeclaration")

package modules

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import domain.UserEntity
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseProvider {

    private lateinit var readWriteDb: Database
    lateinit var readOnlyDb: Database

    fun initialize() {
        readOnlyDb = Database.connect(HikariDataSource(
            HikariConfig().apply {
                jdbcUrl = "jdbc:postgresql://localhost:5433/testdb"
                driverClassName = "org.postgresql.Driver"
                username = "postgres"
                password = "postgres"
            }
        ))

        /*
        * Make sure this connection is established AFTER the read only connection.
        * Latest connection is the default connection in Jetbrains Exposed.
         */
        readWriteDb = Database.connect(HikariDataSource(
            HikariConfig().apply {
                jdbcUrl = "jdbc:postgresql://localhost:5432/testdb"
                driverClassName = "org.postgresql.Driver"
                username = "postgres"
                password = "postgres"
            }
        ))

        schemaInitialize()
    }

    private fun schemaInitialize() {
        transaction {
            SchemaUtils.create(UserEntity)
        }
    }
}
