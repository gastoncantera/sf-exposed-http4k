import controller.UserController
import org.http4k.core.HttpHandler
import org.http4k.core.then
import org.http4k.filter.DebuggingFilters.PrintRequestAndResponse
import org.http4k.server.Netty
import org.http4k.server.asServer
import modules.DatabaseProvider
import org.http4k.core.Method
import org.http4k.routing.bind
import org.http4k.routing.routes

fun main() {
    DatabaseProvider.initialize()

    val httpHandler: HttpHandler = PrintRequestAndResponse().then(
        routes(
            "/api/v1/users" bind Method.GET to UserController.getUserHandler,
            "/api/v1/users/{userId}" bind Method.GET to UserController.getUsersHandler,
            "/api/v1/users" bind Method.POST to UserController.createUserHandler
        )
    )

    val server = httpHandler.asServer(Netty(8080)).start()

    println("Server started on " + server.port())

    server.block()
}
