package me.afmiguez.projects.challenge

import io.ktor.server.application.*
import io.ktor.server.netty.*
import me.afmiguez.projects.challenge.plugins.*

fun main(args:Array<String>) {

    EngineMain.main(args)

}

fun Application.module(){
    configureDI()
    configureDatabase()
    configureRouting()
    configureSecurity()
    configureHTTP()
    configureSerialization()
}

