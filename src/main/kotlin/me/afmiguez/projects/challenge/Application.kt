package me.afmiguez.projects.challenge

import io.ktor.server.application.*
import io.ktor.server.netty.*
import me.afmiguez.projects.challenge.data.DatabaseFactory
import me.afmiguez.projects.challenge.di.koinModules
import me.afmiguez.projects.challenge.plugins.configureHTTP
import me.afmiguez.projects.challenge.plugins.configureRouting
import me.afmiguez.projects.challenge.plugins.configureSecurity
import me.afmiguez.projects.challenge.plugins.configureSerialization

fun main(args:Array<String>) {

    EngineMain.main(args)

}

fun Application.module(){
    configureDI()
    configureRouting()
    configureSecurity()
    configureHTTP()
    configureSerialization()
    DatabaseFactory.init()
}

fun Application.configureDI(){
    install(Koin){
        modules= arrayListOf(koinModules)
    }
}