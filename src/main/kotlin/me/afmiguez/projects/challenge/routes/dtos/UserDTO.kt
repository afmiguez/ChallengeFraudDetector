package me.afmiguez.projects.challenge.routes.dtos

import kotlinx.serialization.Serializable
import me.afmiguez.projects.challenge.models.User

@Serializable
class UserDTO(private val name:String, private val email:String,private val isEnable:Boolean=true,private val id:Int ) {
    constructor(user:User):this(name = user.name, email = user.email,isEnable=user.isEnable,id=user.id)
}