@file:Suppress("InvalidPackageDeclaration")

package domain

data class User(
    val id: UserId,
    val name: String,
    val age: Int,
)

@JvmInline
value class UserId(val value: Long)
