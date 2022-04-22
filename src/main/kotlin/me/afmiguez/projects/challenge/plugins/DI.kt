package me.afmiguez.projects.challenge.plugins

import io.ktor.server.application.*
import me.afmiguez.projects.challenge.Koin
import me.afmiguez.projects.challenge.di.koinModules

fun Application.configureDI(){
    install(Koin){
        modules= arrayListOf(koinModules)
    }
}