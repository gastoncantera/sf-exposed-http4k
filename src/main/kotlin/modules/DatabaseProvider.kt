@file:Suppress("InvalidPackageDeclaration")

package modules

import domain.UserEntity
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseProvider {

    val readOnlyDb = Database.connect(
        "jdbc:postgresql://localhost:5433/testdb",
        driver = "org.postgresql.Driver",
        user = "postgres",
        password = "postgres"
    )

    fun initialize() {
        // latest connection is the default exposed connection
        Database.connect(
            "jdbc:postgresql://localhost:5432/testdb",
            driver = "org.postgresql.Driver",
            user = "postgres",
            password = "postgres"
        )
        schemaInitialize()
    }

    private fun schemaInitialize() {
        transaction {
            SchemaUtils.create(UserEntity)
        }
    }
}
