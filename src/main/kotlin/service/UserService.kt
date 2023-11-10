@file:Suppress("InvalidPackageDeclaration")

package service

import domain.User
import domain.UserEntity
import domain.UserId
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insertAndGetId
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.update
import org.jetbrains.exposed.sql.transactions.transaction
import modules.DatabaseProvider

class UserService {

    fun findUserById(id: UserId): User? {
        return transaction(db = DatabaseProvider.readOnlyDb) {
            UserEntity.select { UserEntity.id eq id.value }.firstOrNull()?.let {
                User(
                    id = UserId(it[UserEntity.id].value),
                    name = it[UserEntity.name],
                    age = it[UserEntity.age],
                )
            }
        }
    }

    fun create(request: UserCreateRequest): UserId {
        val id = transaction {
            UserEntity.insertAndGetId {
                it[name] = request.name
                it[age] = request.age
            }
        }

        return UserId(id.value)
    }

    fun update(id: Long, request: UserUpdateRequest) {
        UserEntity.update({ UserEntity.id eq id }) {
            request.name?.let { name -> it[UserEntity.name] = name }
            request.age?.let { age -> it[UserEntity.age] = age }
        }
    }

    fun delete(id: UserId) {
        UserEntity.deleteWhere { UserEntity.id eq id.value }
    }
}

data class UserCreateRequest(
    val name: String,
    val age: Int,
)

data class UserUpdateRequest(
    val name: String? = null,
    val age: Int? = null,
)
