package me.afmiguez.projects.challenge.models

const val userDefaultEmail="admin@email.com.br"
const val userDefaultName="Admin"
const val userDefaultPassword="123999"


data class User(val name:String, val email:String, val password:String,val imports:List<Import> = emptyList(),val isEnable:Boolean=true, var id: Int=0){

    fun isDefaultUser():Boolean{
        return email== userDefaultEmail
    }
}