package controller

import domain.User
import service.UserRequest
import service.UserService
import org.http4k.core.*
import org.http4k.routing.bind
import org.http4k.routing.path
import org.http4k.routing.routes
import org.http4k.format.Jackson.auto

object UserController {

    private val userService = UserService()

    val app: HttpHandler = routes(

        "/api/v1/users" bind Method.GET to {
            val users = userService.findUsers()
            Response(Status.OK).with(Body.auto<List<User>>().toLens() of users)
        },

        "/api/v1/users/{userId}" bind Method.GET to { request ->
            val userId = request.path("userId")?.toLong() ?: 0
            val user = userService.findUserById(userId)
            if (user != null) {
                Response(Status.OK).with(Body.auto<User>().toLens() of user)
            } else {
                Response(Status.NOT_FOUND)
            }
        },

        "/api/v1/users" bind Method.POST to { request ->
            val userRequest = Body.auto<UserRequest>().toLens()(request)
            val userId = userService.create(userRequest)
            Response(Status.OK).with(Body.auto<Long>().toLens() of userId)
        }

    )
}