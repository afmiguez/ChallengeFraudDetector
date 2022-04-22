package me.afmiguez.projects.challenge.plugins

import io.ktor.server.application.*
import me.afmiguez.projects.challenge.data.DatabaseFactory

fun Application.configureDatabase(){
    DatabaseFactory.init()
}