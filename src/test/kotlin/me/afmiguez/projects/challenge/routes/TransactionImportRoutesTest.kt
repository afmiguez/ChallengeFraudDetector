package me.afmiguez.projects.challenge.routes;

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.server.testing.*
//import me.afmiguez.projects.challenge.plugins.configureDatabaseTest
import me.afmiguez.projects.challenge.plugins.configureRouting
import me.afmiguez.projects.challenge.plugins.configureSecurity
import java.util.*
import kotlin.test.Test
import kotlin.test.assertTrue

class TransactionImportRoutesTest {

    val secret = "jwt.secret"
    val issuer = "jwt.issuer"
    val audience = "jwt.audience"
    val myRealm = "jwt.realm"

    @Test
    fun testGetImports() = testApplication {
//        application {
//            configureRouting()
//            configureSecurity()
//            configureDatabaseTest()
//        }


        client.get("/imports").apply {
            assertTrue(this.status.value==401)
        }

        /*
        BCrypt.hashpw(user.password, BCrypt.gensalt(12))


        val token = JWT.create()
                .withAudience(audience)
                .withIssuer(issuer)
                .withClaim("username", credentials.email)
                .withExpiresAt(Date(System.currentTimeMillis() + 1200000))
                .sign(Algorithm.HMAC256(secret))
         */

        val token = JWT.create()
            .withAudience(audience)
            .withIssuer(issuer)
            .withClaim("username", "userEmail")
            .withExpiresAt(Date(System.currentTimeMillis() + 1200000))
            .sign(Algorithm.HMAC256(secret))

        client.get("/imports"){
            headers{
                append(HttpHeaders.Authorization,token)
            }
        }.apply {
//            assertTrue(this.status.isSuccess())
        }
    }
}