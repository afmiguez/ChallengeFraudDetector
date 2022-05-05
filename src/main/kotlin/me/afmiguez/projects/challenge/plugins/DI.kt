package me.afmiguez.projects.challenge.plugins

import io.ktor.server.application.*
import me.afmiguez.projects.challenge.Koin
import me.afmiguez.projects.challenge.di.koinModules

fun Application.configureDI(){

    println()
    println()
    println()
    println()
    println()
    println("DI")
    println()
    println()
    println()
    println()
    println()

    install(Koin){
        modules= arrayListOf(koinModules)
    }
}