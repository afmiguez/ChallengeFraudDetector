package me.afmiguez.projects.challenge.dao

import me.afmiguez.projects.challenge.models.Import

interface ImportDAO {
    suspend fun save(import: Import, userEmail: String):Import
    suspend fun getAllImports(): List<Import>
    suspend fun getById(id: Int): Import?
}