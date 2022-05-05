package me.afmiguez.projects.challenge

import io.ktor.client.request.*
import io.ktor.server.testing.*
import me.afmiguez.projects.challenge.plugins.configureRouting
import kotlin.math.abs
import kotlin.random.Random
import kotlin.test.Test

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        application {
            configureRouting()
        }
        client.get("/").apply {

        }
    }

}