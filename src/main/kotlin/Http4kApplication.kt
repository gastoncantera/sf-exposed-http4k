import controller.UserController
import org.http4k.core.HttpHandler
import org.http4k.core.then
import org.http4k.filter.DebuggingFilters.PrintRequest
import org.http4k.server.SunHttp
import org.http4k.server.asServer
import modules.DatabaseProvider

fun main() {
    DatabaseProvider.initialize()

    val printingApp: HttpHandler = PrintRequest().then(UserController.app)

    val server = printingApp.asServer(SunHttp(8080)).start()

    println("Server started on " + server.port())
}
