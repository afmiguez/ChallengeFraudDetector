package me.afmiguez.projects.challenge.usecases

import me.afmiguez.projects.challenge.dao.UserDAO
import me.afmiguez.projects.challenge.models.User
import me.afmiguez.projects.challenge.models.userDefaultEmail
import me.afmiguez.projects.challenge.routes.dtos.CredentialsDTO
import org.mindrot.jbcrypt.BCrypt
import kotlin.math.abs
import kotlin.random.Random

/**
 * Apenas 2 informações serão necessárias no cadastro: Nome e Email, sendo ambas obrigatórias OK
A aplicação deve gerar uma senha aleatória para o usuário, composta de 6 números; OK
A senha deverá ser enviada para o email do usuário sendo cadastrado;
A senha não deve ser armazenada no banco de dados em texto aberto, devendo ser salvo um hash dela, gerado pelo algoritmo BCrypt;
A aplicação não deve permitir o cadastro de um usuário com o email de outro usuário já cadastrado, devendo exibir uma mensagem de erro caso essa situação ocorra;
A aplicação deve ter um usuário padrão previamente cadastrado, com nome: Admin, email: admin@email.com.br e senha: 123999;
O usuário padrão não pode ser editado e nem excluído da aplicação, tampouco deve ser exibido na lista de usuários cadastrados;
Qualquer usuário tem permissão para listar, cadastrar, alterar e excluir outros usuários;
Um usuário não pode excluir ele próprio da aplicação.
 */


class UsersServiceImpl(private val userDAO: UserDAO) : UsersService {
    override suspend fun registerUser(name: String, email: String): User {
        if (userDAO.hasEmailAlreadyRegistered(email)) {
            throw IllegalArgumentException("Email already been used")
        }
        val generatedPassword = generatePassword()
        val createdUser = userDAO.createUser(
            User(
                name = name,
                email = email,
                password = generatedPassword,
            )
        )
        sendPasswordByMail(email, password = generatedPassword)
        return createdUser
    }

    override suspend fun getAllUsers(): List<User> =userDAO.findAllUsers().filter { it.isEnable }//.filter { !it.isDefaultUser() }

    override suspend fun updateUser(email: String, newUserInformation: User): Boolean {
        if(email== userDefaultEmail){
            throw IllegalArgumentException("Cannot update default user")
        }
        return userDAO.updateUser(email,newUserInformation)
    }


    private fun generatePassword(): String {
        while (true) {
            val pass = abs(Random.nextInt() % 1000000).toString()
            if (pass.length == 6) {
                return pass
            }
        }
    }

    override fun sendPasswordByMail(email: String, password: String) {
        println("Password $password sent to $email")
    }

    override suspend fun deleteUser(id: Int, authenticatedUserEmail:String): Boolean {
        val userToBeDeleted = userDAO.findById(id)
        if (userToBeDeleted.email == authenticatedUserEmail && userToBeDeleted.isDefaultUser()) {
            throw IllegalArgumentException("User cannot be deleted")
        }
        return userDAO.deleteById(id)
    }

    override suspend fun authenticate(credentials: CredentialsDTO): Boolean {
        val userFromDb=userDAO.findByEmail(credentials.email) ?: return false
        if(userFromDb.isEnable) {
            return BCrypt.checkpw(credentials.password, userFromDb.password)
        }
        return false
    }
}