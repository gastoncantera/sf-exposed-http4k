package modules

import service.UserService

object ServiceProvider {
    val userService by lazy {
        UserService()
    }
}