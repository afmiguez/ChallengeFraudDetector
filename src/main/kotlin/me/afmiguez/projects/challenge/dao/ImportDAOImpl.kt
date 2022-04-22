package me.afmiguez.projects.challenge.dao

import me.afmiguez.projects.challenge.data.entities.ImportEntity
import me.afmiguez.projects.challenge.models.Import

class ImportDAOImpl : ImportDAO {
    override suspend fun save(import: Import): Import {
        return ImportEntity.save(import)
    }

    override suspend fun getAllImports(): List<Import> {
        return ImportEntity.getAll()
    }
}