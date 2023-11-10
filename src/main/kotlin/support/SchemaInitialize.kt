@file:Suppress("InvalidPackageDeclaration")

package support

import domain.UserEntity
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

object SchemaInitialize {
    fun run() {
        Database.connect("jdbc:postgresql://localhost:5432/testdb", driver = "org.postgresql.Driver", user = "postgres", password = "postgres")
        transaction {
            SchemaUtils.create(UserEntity)
        }
    }
}
