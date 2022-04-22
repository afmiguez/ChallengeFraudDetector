package me.afmiguez.projects.challenge.dao

import me.afmiguez.projects.challenge.models.Import

interface ImportDAO {
    suspend fun save(import: Import):Import
    suspend fun getAllImports(): List<Import>
}