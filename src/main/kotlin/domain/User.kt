@file:Suppress("InvalidPackageDeclaration")

package domain

data class User(
    val id: Long,
    val name: String,
    val age: Int,
)