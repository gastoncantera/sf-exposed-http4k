@file:Suppress("InvalidPackageDeclaration")

package domain

//import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.dao.LongIdTable

object UserEntity : LongIdTable() {
    val name = varchar("name", length = 50)
    val age = integer("age")
}
