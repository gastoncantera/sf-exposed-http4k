package controller

import domain.User
import domain.UserId
import service.UserCreateRequest
import service.UserService
import org.http4k.core.*
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.http4k.format.Jackson.auto

object UserController {

    private val userService = UserService()
    private val userLens = Body.auto<User>().toLens()

    val app: HttpHandler = routes(

        "/api/v1/users/{userId}" bind Method.GET to { request ->
            val userId = request.path("userId")?.toLong() ?: 0
            val user = userService.findUserById(UserId(userId))
            if (user != null) {
                Response(Status.OK).with(userLens of user)
            } else {
                Response(Status.NOT_FOUND)
            }
        },

        "/api/v1/users" bind Method.POST to { request ->
            val userRequest = Body.auto<UserCreateRequestForm>().toLens()(request)
            val userId = userService.create(
                UserCreateRequest(
                    name = userRequest.name,
                    age = userRequest.age,
                )
            )
            Response(Status.OK).with(userLens of User(userId, userRequest.name, userRequest.age))
        }

    )

    data class UserCreateRequestForm(
        val name: String,
        val age: Int,
    )
}