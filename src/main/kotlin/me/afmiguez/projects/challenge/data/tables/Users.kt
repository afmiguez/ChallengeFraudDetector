package me.afmiguez.projects.challenge.data.tables

import org.jetbrains.exposed.dao.id.IntIdTable


object Users : IntIdTable() {
    val name=varchar("name",50)
    val email=varchar("email",50)
    val password=varchar("password",200)
    val enable=bool("enable")


}