@file:Suppress("InvalidPackageDeclaration")

package service

import domain.User
import domain.UserEntity
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import modules.DatabaseProvider

class UserService {

    fun findUsers(): List<User> {
        return transaction(db = DatabaseProvider.readOnlyDb) {
            UserEntity.selectAll().map {
                User(
                    id = it[UserEntity.id].value,
                    name = it[UserEntity.name],
                    age = it[UserEntity.age],
                )
            }
        }
    }

    fun findUserById(id: Long): User? {
        return transaction(db = DatabaseProvider.readOnlyDb) {
            UserEntity.select { UserEntity.id eq id }.firstOrNull()?.let {
                User(
                    id = it[UserEntity.id].value,
                    name = it[UserEntity.name],
                    age = it[UserEntity.age],
                )
            }
        }
    }

    fun create(request: UserRequest): Long {
        val id = transaction {
            UserEntity.insertAndGetId {
                it[name] = request.name
                it[age] = request.age
            }
        }

        return id.value
    }
}

data class UserRequest(
    val name: String,
    val age: Int,
)
