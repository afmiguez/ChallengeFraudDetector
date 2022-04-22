package me.afmiguez.projects.challenge.data

import org.jetbrains.exposed.sql.Database

object DatabaseFactoryTest {

    fun init(){
        val driverClassName = "org.h2.Driver"
        val jdbcURL = "jdbc:h2:mem:db1;DB_CLOSE_DELAY=-1;"

        val user="root"
        val password=""
        Database.connect(jdbcURL, driverClassName, user = user,password=password)
        createTables()

    }
}