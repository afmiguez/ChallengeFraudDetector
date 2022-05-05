package me.afmiguez.projects.challenge.routes.dtos

import kotlinx.serialization.Serializable

@Serializable
class CredentialsDTO(val email:String,val password:String)