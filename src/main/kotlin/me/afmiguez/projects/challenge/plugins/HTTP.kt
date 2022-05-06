package me.afmiguez.projects.challenge.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.*



fun Application.configureHTTP() {
    install(CORS) {
        //allowHost("localhost:3000")
        allowHost("afmiguez.me:5000")
        allowHeader(HttpHeaders.ContentType)
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        //anyHost() // @TODO: Don't do this in production if possible. Try to limit it.
    }

}
