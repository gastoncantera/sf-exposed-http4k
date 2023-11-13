package controller

import domain.User
import modules.ServiceProvider
import service.UserRequest
import org.http4k.core.*
import org.http4k.format.Jackson.auto
import org.http4k.lens.Path
import org.http4k.lens.long

object UserController {

    private val userService = ServiceProvider.userService

    private val userIdLens = Path.long().of("userId")
    private val userRequestLens = Body.auto<UserRequest>().toLens()

    val getUserHandler: HttpHandler = { _ ->
        val users = userService.findUsers()
        Response(Status.OK).with(Body.auto<List<User>>().toLens() of users)
    }

    val getUsersHandler: HttpHandler = { request: Request ->
        val userId = userIdLens(request)
        val user = userService.findUserById(userId)
        if (user != null) {
            Response(Status.OK).with(Body.auto<User>().toLens() of user)
        } else {
            Response(Status.NOT_FOUND)
        }
    }

    val createUserHandler: HttpHandler = { request: Request ->
        val userRequest = userRequestLens(request)
        val userId = userService.create(userRequest)
        Response(Status.OK).with(Body.auto<Long>().toLens() of userId)
    }
}