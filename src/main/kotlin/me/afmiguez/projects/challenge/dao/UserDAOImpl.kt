package me.afmiguez.projects.challenge.dao

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.afmiguez.projects.challenge.data.entities.UserEntity
import me.afmiguez.projects.challenge.models.User
import me.afmiguez.projects.challenge.models.userDefaultEmail
import me.afmiguez.projects.challenge.models.userDefaultName
import me.afmiguez.projects.challenge.models.userDefaultPassword

class UserDAOImpl : UserDAO {

    init {
        CoroutineScope(Dispatchers.IO).launch {
            withContext(Dispatchers.IO) {
                if (findAllUsers().isEmpty()) {
                    createUser(
                        User(name = userDefaultName, email = userDefaultEmail, password = userDefaultPassword)
                    )
                    createUser(
                        User(name = "Alessandro", email = "afmiguez@gmail.com", password = "12345")
                    )
                    createUser(
                        User(name = "Sandro", email = "afmiguez@outlook.com", password = "12345")
                    )

                }
            }
        }
    }

    override suspend fun createUser(user: User): User {
        return UserEntity.save(user)
    }

    override suspend fun findAllUsers(): List<User> {
        return UserEntity.listAll()
    }

    override suspend fun findById(id: Int): User {
        return UserEntity.findUserById(id) ?: throw IllegalArgumentException("Does not exists user with id $id")
    }

    override suspend fun deleteById(id: Int): Boolean {
        return UserEntity.deleteUser(id)
    }

    override suspend fun hasEmailAlreadyRegistered(email: String): Boolean {
        return UserEntity.hasEmailAlreadyRegistered(email)
    }

    override suspend fun updateUser(email: String, updatedUserInformation: User): Boolean {
        return UserEntity.updateUser(email, updatedUserInformation)
    }

    override suspend fun findByEmail(email: String): User? {
        return try{
            UserEntity.findByEmail(email)
        }catch (e:Exception){
            null
        }
    }
}