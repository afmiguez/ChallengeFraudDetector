package me.afmiguez.projects.challenge.data.entities

import me.afmiguez.projects.challenge.data.DatabaseFactory
import me.afmiguez.projects.challenge.data.DatabaseFactory.dbQuery
import me.afmiguez.projects.challenge.data.entities.TransactionEntity.Companion.referrersOn
import me.afmiguez.projects.challenge.data.tables.Imports
import me.afmiguez.projects.challenge.data.tables.Users
import me.afmiguez.projects.challenge.models.User
import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt

class UserEntity(id: EntityID<Int>) : IntEntity(id) {

    var name by Users.name
    var email by Users.email
    var password by Users.password
    var enable by Users.enable
    val imports by ImportEntity referrersOn Imports.user

    companion object : IntEntityClass<UserEntity>(Users) {
        suspend fun save(user: User): User {
            return DatabaseFactory.dbQuery {
                new {
                    name = user.name
                    email = user.email
                    password = BCrypt.hashpw(user.password, BCrypt.gensalt(12))
                    enable = true
                }.toModel()
            }
        }

        suspend fun listAll(): List<User> {

            return DatabaseFactory.dbQuery {
                all().map {
                    it.toModel()
                }
            }
        }

        suspend fun findUserById(id:Int):User?{
            return DatabaseFactory.dbQuery {
                findById(id)
            }?.toModel()
        }

        suspend fun deleteUser(id:Int):Boolean{
            return dbQuery {
                val user = findById(id) ?: return@dbQuery false
                //user.delete()
                user.enable = false
                return@dbQuery true
            }
        }

        suspend fun updateUser(email:String,updatedUserInfo:User):Boolean{
            return dbQuery {
                val user=UserEntity.find { Users.email eq email }.first()
                with(user){
                    name=updatedUserInfo.name
                    password=BCrypt.hashpw(updatedUserInfo.password,BCrypt.gensalt(12))
                    enable=updatedUserInfo.isEnable
                }
                true
            }
        }

        suspend fun hasEmailAlreadyRegistered(email:String):Boolean{
            return dbQuery {
                !UserEntity.find {
                    Users.email eq email
                }.empty()
            }
        }

        suspend fun findByEmail(email: String): User {
            return dbQuery {
                UserEntity.find {
                    Users.email eq email
                }.first().toModel()
            }
        }

    }

    fun toModel(): User {
        return User(
            name = name,
            email = email,
            password = password,
            id=id.value,
            isEnable = enable
        )
    }

}