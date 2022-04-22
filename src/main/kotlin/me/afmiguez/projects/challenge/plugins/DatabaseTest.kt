package me.afmiguez.projects.challenge.plugins

import io.ktor.server.application.*
import me.afmiguez.projects.challenge.data.DatabaseFactoryTest

fun Application.configureDatabaseTest(){
    DatabaseFactoryTest.init()
}