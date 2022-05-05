package me.afmiguez.projects.challenge.usecases

import me.afmiguez.projects.challenge.models.User
import me.afmiguez.projects.challenge.routes.dtos.CredentialsDTO

interface UsersService {
    suspend fun registerUser(name:String, email:String):User
    suspend fun getAllUsers():List<User>
    suspend fun updateUser(email:String, newUserInformation:User):Boolean
    fun sendPasswordByMail(email:String, password:String)
    suspend fun deleteUser(id:Int, authenticatedUserEmail: String):Boolean
    suspend fun authenticate(credentials: CredentialsDTO): Boolean
}