package me.afmiguez.projects.challenge.dao

import me.afmiguez.projects.challenge.models.User

interface UserDAO {
    suspend fun createUser(user:User):User
    suspend fun findAllUsers():List<User>
    suspend fun findById(id:Int):User
    suspend fun deleteById(id:Int):Boolean
    suspend fun hasEmailAlreadyRegistered(email:String):Boolean
    suspend fun updateUser(email:String, updatedUserInformation:User):Boolean
    suspend fun findByEmail(email: String): User?
}
