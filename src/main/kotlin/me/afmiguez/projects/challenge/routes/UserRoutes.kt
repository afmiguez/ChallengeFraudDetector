package me.afmiguez.projects.challenge.routes

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.util.pipeline.*
import me.afmiguez.projects.challenge.routes.dtos.CredentialsDTO
import me.afmiguez.projects.challenge.routes.dtos.UserDTO
import me.afmiguez.projects.challenge.usecases.UsersService
import java.util.*

fun Route.userRoutes(usersService: UsersService) {

    val secret = environment?.config?.property("jwt.secret")?.getString()
    val issuer = environment?.config?.property("jwt.issuer")?.getString()
    val audience = environment?.config?.property("jwt.audience")?.getString()

    post("/login") {
        val credentials = call.receive<CredentialsDTO>()
        if (usersService.authenticate(credentials)) {
            val token = JWT.create()
                .withAudience(audience)
                .withIssuer(issuer)
                .withClaim("username", credentials.email)
                .withExpiresAt(Date(System.currentTimeMillis() + 1200000))
                .sign(Algorithm.HMAC256(secret))
            call.respond(hashMapOf("token" to token))
        }
    }

    route("/users") {

        authenticate("auth-jwt") {

            get("") {
                call.respond(usersService.getAllUsers().map { UserDTO(it) })
            }

            delete("/{id?}") {
                val id = call.parameters["id"]?.toInt() ?: return@delete call.respondText(
                    status = HttpStatusCode.BadRequest,
                    text = "Missing id"
                )
                val principalEmail = call.principal<JWTPrincipal>()!!.payload.getClaim("username").asString()
                try{
                    val logicDeleteResult = usersService.deleteUser(id, principalEmail)
                    if (logicDeleteResult) {
                        return@delete call.respondText(
                            status = HttpStatusCode.NoContent,
                            text = "User deleted"
                        )
                    }
                    return@delete call.respondText(
                        status = HttpStatusCode.NotFound,
                        text = "Cannot delete user with email $principalEmail"
                    )
                }catch (e:IllegalArgumentException){
                    call.respondText (
                        status = HttpStatusCode.BadRequest,
                        text = "User cannot delete itself"
                    )
                }

            }
        }
    }

}