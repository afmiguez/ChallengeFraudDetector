package me.afmiguez.projects.challenge.dao

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import me.afmiguez.projects.challenge.data.entities.ImportEntity
import me.afmiguez.projects.challenge.models.Import


class ImportDAOImpl : ImportDAO {
    override suspend fun save(import: Import, userEmail: String): Import {
        return ImportEntity.save(import, userEmail)
    }

    override suspend fun getAllImports(): List<Import> {
        return ImportEntity.getAll()
    }

    override suspend fun getById(id: Int): Import? {
        return ImportEntity.getById(id)
    }
}